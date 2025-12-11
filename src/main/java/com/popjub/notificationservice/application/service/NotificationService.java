package com.popjub.notificationservice.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.popjub.notificationservice.application.dto.command.CreateNotiCommand;
import com.popjub.notificationservice.domain.entity.ChannelType;
import com.popjub.notificationservice.domain.entity.Notification;
import com.popjub.notificationservice.domain.entity.NotificationChannelLog;
import com.popjub.notificationservice.domain.repository.ChannelLogRepository;
import com.popjub.notificationservice.domain.repository.NotificationRepository;
import com.popjub.notificationservice.infrastructure.client.UserClient;
import com.popjub.notificationservice.infrastructure.client.dto.UserInfoResponse;
import com.popjub.notificationservice.infrastructure.sender.ChannelSendResult;
import com.popjub.notificationservice.infrastructure.sender.ChannelSender;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {

	private final NotificationRepository notificationRepository;
	private final ChannelLogRepository logRepository;
	private final List<ChannelSender> senders;
	private final UserClient userClient;

	@Transactional
	public Notification createAndSend(CreateNotiCommand command) {
		// 예약 서비스에서 userId 가져오기
		Long targetUserId = command.userId();
		// 유저 서비스에서 webhook URL 조회
		UserInfoResponse webhookInfo = userClient.getUserWebhook(targetUserId);

		String payload = buildPayload(command);
		// TODO: 채널 검증 로직 수정(유저 url받아올때 slack인지 discord인지 확인해야함 detectChannel)
		ChannelType channel = ChannelType.DISCORD;

		Notification notification = command.toNotification(targetUserId, channel, payload);
		notificationRepository.save(notification);
		notificationRepository.flush();

		NotificationChannelLog channelLog = command.toChannelLog(notification, channel, webhookInfo.discordUrl(), payload);
		logRepository.save(channelLog);
		logRepository.flush();


		ChannelSender sender = senders.stream()
			.filter(s -> s.supports(channel))
			.findFirst()
			.orElseThrow(() -> new IllegalStateException("지원하지 않는 채널"));

		ChannelSendResult result = sender.send(
			webhookInfo.discordUrl(),
			payload
		);

		if (result.success()) {
			channelLog.success(result.responseCode(), result.responseBody());
			notification.markSent();
		} else {
			notification.markFailed();
			channelLog.fail(result.responseCode(), result.responseBody(), result.errorMessage());
		}
		return notification;
	}

	// 알림 메시지 합성
	private String buildPayload(CreateNotiCommand command) {
		return """
		{
		  "content": "📢 예약 알림\\n매장: %s\\n예약일: %s\\n시작시간: %s"
		}
		""".formatted(
				command.storeName(),
				command.reservationDate(),
				command.startTime()
		);
	}
}

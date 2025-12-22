package com.popjub.notificationservice.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.popjub.notificationservice.application.dto.command.NotificationCommand;
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
	public Notification createAndSend(NotificationCommand command) {
		// 예약 서비스에서 userId 가져오기
		Long targetUserId = command.userId();
		// 유저 서비스에서 webhook URL 조회
		UserInfoResponse userInfo = userClient.getUserInfo(targetUserId);

		ChannelType channel = detectChannel(userInfo);
		String targetUrl = getTargetUrl(userInfo, channel);
		String payload = buildPayload(channel, userInfo, command);

		Notification notification = command.toNotification(targetUserId, channel, payload);
		notificationRepository.save(notification);
		notificationRepository.flush();

		NotificationChannelLog channelLog = command.toChannelLog(notification, channel, targetUrl, payload);
		logRepository.save(channelLog);
		logRepository.flush();

		ChannelSender sender = senders.stream()
			.filter(s -> s.supports(channel))
			.findFirst()
			.orElseThrow(() -> new IllegalStateException("지원하지 않는 채널"));

		ChannelSendResult result = sender.send(
			targetUrl,
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

	private ChannelType detectChannel(UserInfoResponse userInfo) {
		if (userInfo.discordUrl() != null && !userInfo.discordUrl().isBlank()) {
			return ChannelType.DISCORD;
		}
		if (userInfo.slackUrl() != null && !userInfo.slackUrl().isBlank()) {
			return ChannelType.SLACK;
		}
		throw new IllegalStateException("user has no notification channel");
	}

	private String getTargetUrl(UserInfoResponse userInfo, ChannelType channel) {
		return channel == ChannelType.DISCORD ? userInfo.discordUrl() : userInfo.slackUrl();
	}

	public String buildPayload(ChannelType channel, UserInfoResponse userInfo, NotificationCommand command) {
		return switch (channel) {
			case SLACK -> command.buildSlackPayload(userInfo);
			case DISCORD -> command.buildDiscordPayload(userInfo);
		};
	}
}

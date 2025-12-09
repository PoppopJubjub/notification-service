package com.popjub.notificationservice.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.popjub.notificationservice.application.dto.command.CreateNotiCommand;
import com.popjub.notificationservice.domain.entity.Notification;
import com.popjub.notificationservice.domain.entity.NotificationChannelLog;
import com.popjub.notificationservice.domain.repository.ChannelLogRepository;
import com.popjub.notificationservice.domain.repository.NotificationRepository;
import com.popjub.notificationservice.infrastructure.sender.ChannelSendResult;
import com.popjub.notificationservice.infrastructure.sender.ChannelSender;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {

	private final NotificationRepository notificationRepository;
	private final ChannelLogRepository logRepository;
	private final List<ChannelSender> senders;

	@Transactional
	public Notification createAndSend(CreateNotiCommand command) {

		Notification notification = command.toNotification();
		notificationRepository.save(notification);

		NotificationChannelLog channelLog = command.toChannelLog(notification);
		logRepository.save(channelLog);

		ChannelSender sender = senders.stream()
			.filter(s -> s.supports(command.channel()))
			.findFirst()
			.orElseThrow(() -> new IllegalStateException("지원하지 않는 채널"));

		ChannelSendResult result = sender.send(
			command.webhookUrl(),
			command.content()
		);

		if (result.success()) {
			notification.markSent();
			channelLog.success(result.responseCode(), result.responseBody());
		} else {
			notification.markFailed();
			channelLog.fail(result.responseCode(), result.responseBody(), result.errorMessage());
		}

		return notification;
	}
}

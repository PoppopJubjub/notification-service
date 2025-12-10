package com.popjub.notificationservice.application.dto.command;

import java.util.UUID;

import com.popjub.notificationservice.domain.entity.ChannelType;
import com.popjub.notificationservice.domain.entity.EventType;
import com.popjub.notificationservice.domain.entity.Notification;
import com.popjub.notificationservice.domain.entity.NotificationChannelLog;

public record CreateNotiCommand(
	Long userId,
	UUID reservationId,
	ChannelType channel,
	String content,
	String webhookUrl,
	EventType type
) {
	public Notification toNotification() {
		return Notification.of(
			userId,
			reservationId,
			channel,
			content,
			type
		);
	}
	public NotificationChannelLog toChannelLog(Notification notification) {
		return NotificationChannelLog.of(
			notification,
			channel,
			webhookUrl,
			content
		);
	}
}

package com.popjub.notificationservice.application.dto.command;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import com.popjub.notificationservice.domain.entity.ChannelType;
import com.popjub.notificationservice.domain.entity.EventType;
import com.popjub.notificationservice.domain.entity.Notification;
import com.popjub.notificationservice.domain.entity.NotificationChannelLog;

public record CreateNotiCommand(
	UUID reservationId,
	Long userId,
	String storeName,
	LocalDate reservationDate,
	LocalTime startTime,
	EventType eventType
) {
	public Notification toNotification(Long userId, ChannelType channel, String content) {
		return Notification.of(
			userId,
			reservationId,
			channel,
			content,
			eventType
		);
	}
	public NotificationChannelLog toChannelLog(Notification notification, ChannelType channel, String webhookUrl, String content) {
		return NotificationChannelLog.of(
			notification,
			channel,
			webhookUrl,
			content // request body
		);
	}
}

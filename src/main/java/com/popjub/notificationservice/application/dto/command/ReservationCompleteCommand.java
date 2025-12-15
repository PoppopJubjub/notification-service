package com.popjub.notificationservice.application.dto.command;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import com.popjub.notificationservice.domain.entity.ChannelType;
import com.popjub.notificationservice.domain.entity.EventType;
import com.popjub.notificationservice.domain.entity.Notification;
import com.popjub.notificationservice.domain.entity.NotificationChannelLog;
import com.popjub.notificationservice.infrastructure.client.dto.UserInfoResponse;

public record ReservationCompleteCommand(
	UUID reservationId,
	Long userId,
	String storeName,
	LocalDate reservationDate,
	LocalTime startTime,
	EventType eventType
) implements NotificationCommand {

	@Override
	public Notification toNotification(Long userId, ChannelType channel, String content) {
		return Notification.of(
			userId,
			reservationId,
			channel,
			content,
			eventType
		);
	}
	@Override
	public NotificationChannelLog toChannelLog(Notification notification, ChannelType channel, String webhookUrl, String content) {
		return NotificationChannelLog.of(
			notification,
			channel,
			webhookUrl,
			content // request body
		);
	}

	@Override
	public String buildPayload(UserInfoResponse userInfo) {
		return """
		{
		  "content": "📢 예약 알림\\n예약자: %s\\n매장: %s\\n예약일: %s\\n시작시간: %s"
		}
		""".formatted(
			userInfo.username(),
			storeName(),
			reservationDate(),
			startTime()
		);
	}
}

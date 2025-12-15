package com.popjub.notificationservice.application.dto.command;

import java.util.UUID;

import com.popjub.notificationservice.domain.entity.ChannelType;
import com.popjub.notificationservice.domain.entity.EventType;
import com.popjub.notificationservice.domain.entity.Notification;
import com.popjub.notificationservice.domain.entity.NotificationChannelLog;
import com.popjub.notificationservice.infrastructure.client.dto.UserInfoResponse;

public record CheckInCommand(
	UUID reservationId,
	Long userId,
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
	public String buildPayload(UserInfoResponse userInfo) {
		// payload 템플릿은 각 커맨드가 책임
		return """
		{
		  "content": "✅ 체크인 완료\\n예약자: %s"
		}
		""".formatted(userInfo.username());
	}
}
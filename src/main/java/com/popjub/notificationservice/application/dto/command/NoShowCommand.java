package com.popjub.notificationservice.application.dto.command;

import java.util.UUID;

import com.popjub.notificationservice.domain.entity.ChannelType;
import com.popjub.notificationservice.domain.entity.EventType;
import com.popjub.notificationservice.domain.entity.Notification;
import com.popjub.notificationservice.domain.entity.NotificationChannelLog;
import com.popjub.notificationservice.infrastructure.client.dto.UserInfoResponse;

public record NoShowCommand (
	UUID reservationId,
	Long userId,
	Integer noShowCount,
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
	public String buildDiscordPayload(UserInfoResponse userInfo) {
		return """
		{
		  "content": "⚠️ 노쇼 경고\\n대상자: %s\\n노쇼 횟수: %s번"
		}
		""".formatted(
			userInfo.userName(),
			noShowCount()
		);
	}

	@Override
	public String buildSlackPayload(UserInfoResponse userInfo) {
		return """
		{
		  "text": "⚠️ 노쇼 경고\\n대상자: %s\\n노쇼 횟수: %s번"
		}
		""".formatted(
			userInfo.userName(),
			noShowCount()
		);
	}
}

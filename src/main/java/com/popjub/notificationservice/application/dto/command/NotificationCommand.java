package com.popjub.notificationservice.application.dto.command;

import com.popjub.notificationservice.domain.entity.ChannelType;
import com.popjub.notificationservice.domain.entity.EventType;
import com.popjub.notificationservice.domain.entity.Notification;
import com.popjub.notificationservice.domain.entity.NotificationChannelLog;
import com.popjub.notificationservice.infrastructure.client.dto.UserInfoResponse;

public interface NotificationCommand {
	Long userId();
	EventType eventType();

	String buildPayload(UserInfoResponse userInfo);
	Notification toNotification(Long targetUserId, ChannelType channel, String payload);
	NotificationChannelLog toChannelLog(Notification notification, ChannelType channel, String targetUrl, String payload);
}
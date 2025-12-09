package com.popjub.notificationservice.presentation.dto.response;

import java.util.UUID;

import com.popjub.notificationservice.domain.entity.Notification;
import com.popjub.notificationservice.domain.entity.NotificationStatus;

public record CreateNotiResponse(
	UUID notificationId,
	NotificationStatus status
) {
	public static CreateNotiResponse from(Notification notification) {
		return new CreateNotiResponse(
			notification.getNotificationId(),
			notification.getStatus()
		);
	}
}

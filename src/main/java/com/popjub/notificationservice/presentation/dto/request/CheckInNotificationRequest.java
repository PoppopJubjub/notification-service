package com.popjub.notificationservice.presentation.dto.request;

import java.util.UUID;

import com.popjub.notificationservice.application.dto.command.CheckInCommand;
import com.popjub.notificationservice.domain.entity.EventType;

public record CheckInNotificationRequest(
	UUID reservationId,
	Long userId,
	EventType eventType
) {
	public CheckInCommand toCommand() {
		return new CheckInCommand(
			this.reservationId,
			this.userId,
			this.eventType
		);
	}
}

package com.popjub.notificationservice.presentation.dto.request;

import java.util.UUID;

import com.popjub.notificationservice.application.dto.command.NoShowCommand;
import com.popjub.notificationservice.domain.entity.EventType;

public record NoShowNotificationRequest(
	UUID reservationId,
	Long userId,
	Integer noShowCount,
	EventType eventType
) {
	public NoShowCommand toCommand() {
		return new NoShowCommand(
			this.reservationId,
			this.userId,
			this.noShowCount,
			this.eventType
		);
	}
}
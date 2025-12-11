package com.popjub.notificationservice.presentation.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import com.popjub.notificationservice.application.dto.command.CreateNotiCommand;
import com.popjub.notificationservice.domain.entity.EventType;

public record ReservationNotificationRequest(
	UUID reservationId,
	Long userId,
	String storeName,
	LocalDate reservationDate,
	LocalTime startTime,
	EventType eventType
) {
	public CreateNotiCommand toCommand() {
		return new CreateNotiCommand(
			this.reservationId,
			this.userId,
			this.storeName,
			this.reservationDate,
			this.startTime,
			this.eventType
		);
	}
}

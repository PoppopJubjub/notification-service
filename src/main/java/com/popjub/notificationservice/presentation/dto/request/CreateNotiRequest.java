package com.popjub.notificationservice.presentation.dto.request;

import java.util.UUID;

import com.popjub.notificationservice.application.dto.command.CreateNotiCommand;
import com.popjub.notificationservice.domain.entity.ChannelType;
import com.popjub.notificationservice.domain.entity.EventType;

public record CreateNotiRequest(
	UUID reservationId,
	ChannelType channel,
	EventType type,
	String content
) {
	// public CreateNotiCommand toCommand() {
	// 	return new CreateNotiCommand();
	// }
}

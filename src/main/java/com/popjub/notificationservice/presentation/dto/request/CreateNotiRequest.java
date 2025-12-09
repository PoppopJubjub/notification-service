package com.popjub.notificationservice.presentation.dto.request;

import java.util.UUID;

import com.popjub.notificationservice.application.dto.command.CreateNotiCommand;
import com.popjub.notificationservice.domain.entity.ChannelType;
import com.popjub.notificationservice.domain.entity.EventType;

public record CreateNotiRequest(
	UUID reservationId,
	ChannelType channel,
	EventType type,
	String webhookUrl,
	String content
) {
	public CreateNotiCommand toCommand(Long userId) {
		return new CreateNotiCommand(
			userId,
			this.reservationId,
			this.channel,
			this.content,
			this.webhookUrl,
			this.type
		);
	}
}

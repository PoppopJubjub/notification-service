package com.popjub.notificationservice.infrastructure.sender;

import com.popjub.notificationservice.domain.entity.ChannelType;

public interface ChannelSender {
	boolean supports(ChannelType channel);
	ChannelSendResult send(String webhookUrl, String content);
}

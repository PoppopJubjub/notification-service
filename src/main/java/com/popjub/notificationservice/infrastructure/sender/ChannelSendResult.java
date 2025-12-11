package com.popjub.notificationservice.infrastructure.sender;

public record ChannelSendResult(
	boolean success,
	String requestBody,
	String responseCode,
	String responseBody,
	String errorMessage
) { }

package com.popjub.notificationservice.infrastructure.client.dto;

public record UserInfoResponse(
	Long userId,
	String discordUrl,
	String slackUrl
) {}

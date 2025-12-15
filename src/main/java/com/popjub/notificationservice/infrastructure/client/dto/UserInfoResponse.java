package com.popjub.notificationservice.infrastructure.client.dto;

public record UserInfoResponse(
	Long userId,
	String username,
	String discordUrl,
	String slackUrl
) {}

package com.popjub.notificationservice.infrastructure.client.dto;

public record UserInfoResponse(
	Long userId,
	String userName,
	String discordUrl,
	String slackUrl
) {}

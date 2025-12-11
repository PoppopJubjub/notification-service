package com.popjub.notificationservice.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.popjub.notificationservice.infrastructure.client.dto.UserInfoResponse;

@FeignClient(
	name = "user-service",
	url = "${service.user.url}"
)
public interface UserClient {
	@GetMapping("internal/users/{userId}/urls")
	UserInfoResponse getUserWebhook(@PathVariable("userId") Long userId);
}
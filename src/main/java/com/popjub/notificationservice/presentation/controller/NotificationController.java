package com.popjub.notificationservice.presentation.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.popjub.common.enums.SuccessCode;
import com.popjub.common.response.ApiResponse;
import com.popjub.notificationservice.application.dto.command.CreateNotiCommand;
import com.popjub.notificationservice.application.service.NotificationService;
import com.popjub.notificationservice.domain.entity.Notification;
import com.popjub.notificationservice.presentation.dto.request.CreateNotiRequest;
import com.popjub.notificationservice.presentation.dto.response.CreateNotiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notifications")
public class NotificationController {
	private final NotificationService notificationService;

	@PostMapping
	public ApiResponse<CreateNotiResponse> createNotification(
		@RequestHeader("X-User-Id") Long userId,
		@RequestBody CreateNotiRequest request){

		CreateNotiCommand command = request.toCommand(userId);
		Notification notification= notificationService.createAndSend(command);
		CreateNotiResponse response = CreateNotiResponse.from(notification);

		return ApiResponse.of(SuccessCode.CREATED, response);
	}
}

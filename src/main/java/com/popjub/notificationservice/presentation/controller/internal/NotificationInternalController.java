package com.popjub.notificationservice.presentation.controller.internal;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.popjub.common.response.ApiResponse;
import com.popjub.notificationservice.application.dto.command.CheckInCommand;
import com.popjub.notificationservice.application.dto.command.ReservationCompleteCommand;
import com.popjub.notificationservice.application.dto.command.NoShowCommand;
import com.popjub.notificationservice.application.service.NotificationService;
import com.popjub.notificationservice.presentation.dto.request.CheckInNotificationRequest;
import com.popjub.notificationservice.presentation.dto.request.NoShowNotificationRequest;
import com.popjub.notificationservice.presentation.dto.request.ReservationNotificationRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/notifications")
public class NotificationInternalController {
	private final NotificationService notificationService;

	@PostMapping("/reservation/create")
	public ApiResponse<?> getReservation(
		@RequestBody ReservationNotificationRequest request) {
		ReservationCompleteCommand command = request.toCommand();
		notificationService.createAndSend(command);
		return ApiResponse.of("예약 완료 알림 생성","");
	}

	@PostMapping("/reservation/check-in")
	public ApiResponse<?> getCheckIn(
		@RequestBody CheckInNotificationRequest request) {
		CheckInCommand command = request.toCommand();
		notificationService.createAndSend(command);
		return ApiResponse.of("체크인 알림 생성","");
	}

	@PostMapping("/reservation/no-show")
	public ApiResponse<?> getNoShow(
		@RequestBody NoShowNotificationRequest request) {
		NoShowCommand command = request.toCommand();
		notificationService.createAndSend(command);
		return ApiResponse.of("노쇼 알림 생성","");
	}
}

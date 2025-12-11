package com.popjub.notificationservice.presentation.controller.internal;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.popjub.common.response.ApiResponse;
import com.popjub.notificationservice.application.dto.command.CreateNotiCommand;
import com.popjub.notificationservice.application.service.NotificationService;
import com.popjub.notificationservice.presentation.dto.request.ReservationNotificationRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/notifications")
public class NotificationInternalController {
	private final NotificationService notificationService;

	/**
	 * 예약서비스 날린거 -> ReservationNotificationRequest로 들어옴!
	 */
	@PostMapping("/reservation/create")
	public ApiResponse<?> getReservation(
		@RequestBody ReservationNotificationRequest request) {
		CreateNotiCommand command = request.toCommand();
		notificationService.createAndSend(command);
		return ApiResponse.of("알림 생성 완료","");
	}
}

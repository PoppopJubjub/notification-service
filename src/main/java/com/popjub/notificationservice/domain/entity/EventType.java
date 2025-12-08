package com.popjub.notificationservice.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventType {
	RESERVATION_COMPLETED("예약 완료"),
	RESERVATION_CANCELED("예약 취소"),
	CHECKIN_COMPLETED("체크인 완료"),
	REMINDER_1D_BEFORE("24시간 전 알람"),
	REMINDER_1H_BEFORE("1시간 전 알람"),
	NO_SHOW_WARNING("노쇼 경고");

	private final String description;
}

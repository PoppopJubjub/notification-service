package com.popjub.notificationservice.domain.entity;

public enum NotificationStatus {
	PENDING, // 생성, 아직 처리 전
	SENT, // 모든 채널 성공
	FAILED, // 모든 채널 실패
	PARTIAL_SUCCESS // 일부 채널만 성공
}

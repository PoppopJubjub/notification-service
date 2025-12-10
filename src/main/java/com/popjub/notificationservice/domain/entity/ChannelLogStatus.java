package com.popjub.notificationservice.domain.entity;

public enum ChannelLogStatus {
	PENDING,	// 전송 대기
	SUCCESS,    // 전송 성공
	FAILED,     // 전송 실패 (일시/영구)
	RETRYING	// 재시도
}

package com.popjub.notificationservice.domain.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "p_notification_channel_log")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationChannelLog {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID channelLogId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "notification_id")
	private Notification notification;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ChannelType channel;

	@Column(nullable = false)
	private String channelUrl;

	private String requestBody;

	private String responseCode;

	@Column(columnDefinition = "TEXT")
	private String responseBody;

	@Enumerated(EnumType.STRING)
	private ChannelLogStatus status = ChannelLogStatus.PENDING;

	private int attempt_cnt = 0;

	private String error_message;

	private LocalDateTime requestAt; // 전송 시도 시각

	private LocalDateTime sentAt; // 전송 완료 시각

	@Builder(access = AccessLevel.PRIVATE)
	private NotificationChannelLog(Notification notification, ChannelType channel, String url, String req){
		this.notification = notification;
		this.channel = channel;
		this.channelUrl = url;
		this.requestBody = req;
		this.requestAt = LocalDateTime.now();
	}

	public static NotificationChannelLog of(Notification notification, ChannelType channel, String url, String req){
		return NotificationChannelLog.builder()
			.notification(notification)
			.channel(channel)
			.url(url)
			.req(req)
			.build();
	}

	public void success(
		String responseCode,
		String responseBody
	) {
		this.status = ChannelLogStatus.SUCCESS;
		this.responseCode = responseCode;
		this.responseBody = responseBody;
		this.sentAt = LocalDateTime.now();
	}

	// TODO: 재시도 로직 구현
	public void retry(
		String responseCode,
		String responseBody,
		String errorMessage
	) {
		this.status = ChannelLogStatus.RETRYING;
		this.responseCode = responseCode;
		this.responseBody = responseBody;
		this.error_message = errorMessage;
		this.attempt_cnt += 1;
	}

	// 실패 (재시도 안함)
	public void fail(
		String responseCode,
		String responseBody,
		String errorMessage
	) {
		this.status = ChannelLogStatus.FAILED;
		this.responseCode = responseCode;
		this.responseBody = responseBody;
		this.error_message = errorMessage;
	}
}

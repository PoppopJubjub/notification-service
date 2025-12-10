package com.popjub.notificationservice.domain.entity;

import java.util.UUID;

import com.popjub.common.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "p_notification")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "notification_id")
	private UUID notificationId;

	@Column(nullable = false)
	private Long userId;

	@Column(nullable = false)
	private UUID reservationId;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ChannelType channel;

	@Column(columnDefinition = "TEXT")
	private String content;

	@Enumerated(EnumType.STRING)
	private EventType type;

	@Enumerated(EnumType.STRING)
	private NotificationStatus status = NotificationStatus.PENDING;

	public void markSent() {
		this.status = NotificationStatus.SENT;
	}

	public void markFailed() {
		this.status = NotificationStatus.FAILED;
	}

	@Builder(access = AccessLevel.PRIVATE)
	private Notification(Long userId, UUID reservationId, ChannelType channel, String content, EventType type) {
		this.userId = userId;
		this.reservationId = reservationId;
		this.channel = channel;
		this.content = content;
		this.type = type;
	}

	public static Notification of(Long userId, UUID reservationId, ChannelType channel, String content, EventType type) {
		return Notification.builder()
			.userId(userId)
			.reservationId(reservationId)
			.channel(channel)
			.content(content)
			.type(type)
			.build();
	}
}

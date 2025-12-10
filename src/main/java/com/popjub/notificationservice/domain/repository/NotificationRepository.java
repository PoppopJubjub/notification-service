package com.popjub.notificationservice.domain.repository;

import java.util.Optional;
import java.util.UUID;

import com.popjub.notificationservice.domain.entity.Notification;

public interface NotificationRepository {
	Notification save(Notification notification);
	boolean existsById(UUID id);
	Optional<Notification> findById(UUID id);
}

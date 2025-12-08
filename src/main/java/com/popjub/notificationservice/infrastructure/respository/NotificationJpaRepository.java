package com.popjub.notificationservice.infrastructure.respository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.popjub.notificationservice.domain.entity.Notification;

public interface NotificationJpaRepository extends JpaRepository<Notification, UUID> {
	boolean existsByNotificationId(UUID notificationId);
	Optional<Notification> findByNotificationId(UUID notificationId);
}

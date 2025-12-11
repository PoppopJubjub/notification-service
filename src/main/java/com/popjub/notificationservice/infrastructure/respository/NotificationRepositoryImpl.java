package com.popjub.notificationservice.infrastructure.respository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.popjub.notificationservice.domain.entity.Notification;
import com.popjub.notificationservice.domain.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepository {

	private final NotificationJpaRepository notificationJpaRepository;

	@Override
	public Notification save(Notification notification) {
		return notificationJpaRepository.save(notification);
	}

	@Override
	public boolean existsById(UUID id) {
		return notificationJpaRepository.existsByNotificationId(id);
	}

	@Override
	public Optional<Notification> findById(UUID id) {
		return notificationJpaRepository.findByNotificationId(id);
	}

	@Override
	public void flush() {
		notificationJpaRepository.flush();
	}
}

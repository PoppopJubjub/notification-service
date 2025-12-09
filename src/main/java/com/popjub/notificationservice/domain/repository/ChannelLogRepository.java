package com.popjub.notificationservice.domain.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.popjub.notificationservice.domain.entity.NotificationChannelLog;

public interface ChannelLogRepository extends JpaRepository<NotificationChannelLog, UUID> {
}

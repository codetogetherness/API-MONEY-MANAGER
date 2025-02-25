package com.backend_api.money_manager.repository;

import com.backend_api.money_manager.entity.NotificationRead;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationReadRepository extends JpaRepository<NotificationRead, String> {
    NotificationRead findByNotificationId(String id);
}

package com.backend_api.money_manager.repository;

import com.backend_api.money_manager.entity.Notification;
import com.backend_api.money_manager.entity.NotificationRead;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, String> {

    @Query("SELECT COUNT(nr) FROM Notification l " +
            "INNER JOIN NotificationRead nr ON l.id = nr.notification.id " +
            "WHERE l.user.id = :user")
    int countDataByUser(@Param("user") String user);

    @Query("SELECT nr FROM Notification l " +
            "INNER JOIN NotificationRead nr ON l.id = nr.notification.id " +
            "WHERE l.user.id = :user")
    List<NotificationRead> findNotificationsForUser(@Param("user") String user, Pageable pageable);


}

package com.backend_api.money_manager.service.notification;

import com.backend_api.money_manager.dto.request.notification.NotificationRequest;
import com.backend_api.money_manager.dto.request.notification.NotificationRequestUser;
import com.backend_api.money_manager.dto.request.notification.PageNotificationRequest;
import org.springframework.http.ResponseEntity;

public interface NotificationService {
    ResponseEntity<Object> create(NotificationRequest request);
    ResponseEntity<Object> findUser(PageNotificationRequest request);
}

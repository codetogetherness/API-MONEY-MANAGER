package com.backend_api.money_manager.service.notification;

import com.backend_api.money_manager.dto.request.notification.NotificationReadRequest;
import org.springframework.http.ResponseEntity;

public interface NotificationReadService {
    ResponseEntity<Object> create(NotificationReadRequest request);
}

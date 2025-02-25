package com.backend_api.money_manager.controller.users.notification;

import com.backend_api.money_manager.dto.request.notification.NotificationReadRequest;
import com.backend_api.money_manager.service.notification.NotificationReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class NotificationReadController {
    @Autowired
    NotificationReadService notificationReadService;

    @PostMapping("/notification/read")
    public ResponseEntity<Object> createNotificationRead(@RequestBody NotificationReadRequest request) {
        System.out.println("=== notif read controller ===");
        System.out.println(request.getNotificationId());
        return notificationReadService.create(request);
    }

}

package com.backend_api.money_manager.controller.users.notification;

import com.backend_api.money_manager.dto.request.notification.NotificationRequest;
import com.backend_api.money_manager.dto.request.notification.NotificationRequestUser;
import com.backend_api.money_manager.dto.request.notification.PageNotificationRequest;
import com.backend_api.money_manager.service.notification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class NotificationController {
    @Autowired
    NotificationService notificationService;

    @PostMapping("/notification")
    public ResponseEntity<Object> createNotification(@RequestBody NotificationRequest request) {
        System.out.println("=== notification controller ===");
        System.out.println(request);
        return notificationService.create(request);
    }

    @PostMapping("/notification/find/user")
    public ResponseEntity<Object> findNotificationUser(@RequestBody PageNotificationRequest request) {
        return notificationService.findUser(request);
    }
}

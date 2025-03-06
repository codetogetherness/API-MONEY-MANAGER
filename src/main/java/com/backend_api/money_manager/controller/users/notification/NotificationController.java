package com.backend_api.money_manager.controller.users.notification;

import com.backend_api.money_manager.dto.request.notification.NotificationRequest;
import com.backend_api.money_manager.dto.request.notification.NotificationRequestUser;
import com.backend_api.money_manager.dto.request.notification.PageNotificationRequest;
import com.backend_api.money_manager.service.notification.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

    @Operation(
            summary = "Secure data",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @PostMapping("/notification")
    public ResponseEntity<Object> createNotification(@RequestBody NotificationRequest request) {
        return notificationService.create(request);
    }

    @Operation(
            summary = "Secure data",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @PostMapping("/notification/find/user")
    public ResponseEntity<Object> findNotificationUser(@RequestBody PageNotificationRequest request) {
        return notificationService.findUser(request);
    }
}

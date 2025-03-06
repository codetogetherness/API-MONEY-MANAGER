package com.backend_api.money_manager.controller.users.notification;

import com.backend_api.money_manager.dto.request.notification.NotificationReadRequest;
import com.backend_api.money_manager.service.notification.NotificationReadService;
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
public class NotificationReadController {
    @Autowired
    NotificationReadService notificationReadService;

    @Operation(
            summary = "Secure data",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @PostMapping("/notification/read")
    public ResponseEntity<Object> createNotificationRead(@RequestBody NotificationReadRequest request) {
        return notificationReadService.create(request);
    }

}

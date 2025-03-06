package com.backend_api.money_manager.controller.users.subscription;

import com.backend_api.money_manager.dto.request.s3bucket.S3ProfileUsersRequest;
import com.backend_api.money_manager.service.subscription.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class SubscriptionController {
    @Autowired
    SubscriptionService subscriptionService;

    @Operation(
            summary = "Secure data",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @GetMapping("/subscriptions")
    public ResponseEntity<Object> updateProfile() {
        return subscriptionService.getListSubscription();
    }

    @Operation(
            summary = "Secure data",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @GetMapping("/cek-subscription")
    public ResponseEntity<Object> cekSubscription() {
        return subscriptionService.getCekSubscription();
    }

}

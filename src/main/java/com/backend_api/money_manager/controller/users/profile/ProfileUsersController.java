package com.backend_api.money_manager.controller.users.profile;

import com.backend_api.money_manager.dto.request.s3bucket.S3ProfileUsersRequest;
import com.backend_api.money_manager.service.profile.ProfileUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
public class ProfileUsersController {
    @Autowired
    ProfileUserService profileUserService;

    @Operation(
            summary = "Secure data",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @PostMapping("/profile/upload/update")
    public ResponseEntity<Object> updateProfile(@RequestBody S3ProfileUsersRequest request) {
        return profileUserService.updateProfileUser(request);
    }
}

package com.backend_api.money_manager.controller.users.profile;

import com.backend_api.money_manager.dto.request.s3bucket.S3ProfileUsersRequest;
import com.backend_api.money_manager.service.profile.ProfileUserService;
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

    @PostMapping("/profile/upload/update")
    public ResponseEntity<Object> updateProfile(@RequestBody S3ProfileUsersRequest request) {
        System.out.println("=== Tmpil data ===");
        return profileUserService.updateProfileUser(request);
    }
}

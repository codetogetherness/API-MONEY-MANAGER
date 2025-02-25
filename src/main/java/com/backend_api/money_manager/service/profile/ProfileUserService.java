package com.backend_api.money_manager.service.profile;

import com.backend_api.money_manager.dto.request.s3bucket.S3ProfileUsersRequest;
import org.springframework.http.ResponseEntity;


public interface ProfileUserService {
    ResponseEntity<Object> updateProfileUser(S3ProfileUsersRequest request);
}

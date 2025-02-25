package com.backend_api.money_manager.service.auth;

import com.backend_api.money_manager.dto.request.users.LoginRequest;
import com.backend_api.money_manager.dto.request.users.RegisterRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<Object> login(LoginRequest request);
    ResponseEntity<Object> register(RegisterRequest request);

}

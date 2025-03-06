package com.backend_api.money_manager.controller;

import com.backend_api.money_manager.dto.request.users.AccountVerification;
import com.backend_api.money_manager.dto.request.users.LoginRequest;
import com.backend_api.money_manager.dto.request.users.RegisterRequest;
import com.backend_api.money_manager.helper.MailHelper;
import com.backend_api.money_manager.service.auth.AuthService;
import com.backend_api.money_manager.service.users.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth Controller", description = "Authentication Service")
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    UsersService usersService;

    @Operation(summary = "Login Service", description = "authentication service")
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest request){
        return authService.login(request);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterRequest request){
        return authService.register(request);
    }

    @Operation(
            summary = "Secure data",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @PostMapping("/verification-account")
    public ResponseEntity<Object> verificationAccount(@RequestBody AccountVerification request){
        return usersService.verificationAccount(request);
    }

}

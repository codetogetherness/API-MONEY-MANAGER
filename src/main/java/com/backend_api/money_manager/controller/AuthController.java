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
        System.out.println("=== login controller ===");
        return authService.login(request);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterRequest request){
        System.out.println("=== register controller ===");
        System.out.println(request);
        return authService.register(request);
    }

    @PostMapping("/verification-account")
    @Parameter(name = "Authorization", description = "Bearer token", required = true, in = ParameterIn.HEADER)
    public ResponseEntity<Object> verificationAccount(@RequestBody AccountVerification request){
        return usersService.verificationAccount(request);
    }

}

package com.backend_api.money_manager.controller.users;

import com.backend_api.money_manager.service.balance.BalanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UsersController {

    @Autowired
    BalanceService balanceService;

    @Operation(
            summary = "Secure data",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @GetMapping("/info-balance")
    public ResponseEntity<Object> balanceUser(){
        return balanceService.getBalanceUser();
    }


}

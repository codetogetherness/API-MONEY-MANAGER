package com.backend_api.money_manager.controller.users;

import com.backend_api.money_manager.service.balance.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UsersController {

    @Autowired
    BalanceService balanceService;

    @GetMapping("/info-balance")
    public ResponseEntity<Object> balanceUser(){
        return balanceService.getBalanceUser();
    }


}

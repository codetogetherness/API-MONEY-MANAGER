package com.backend_api.money_manager.service.balance;

import org.springframework.http.ResponseEntity;

public interface BalanceService {
    ResponseEntity<Object> getBalanceUser();
}

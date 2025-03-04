package com.backend_api.money_manager.dto.request.balance;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class BalanceUserRequest {
    private String user;

    private String value;
}

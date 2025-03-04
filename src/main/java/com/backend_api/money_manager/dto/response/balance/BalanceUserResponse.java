package com.backend_api.money_manager.dto.response.balance;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class BalanceUserResponse {

    private String user;

    private String value;
}

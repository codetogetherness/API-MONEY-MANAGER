package com.backend_api.money_manager.dto.request.users;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AccountVerification {
    private String code;
}

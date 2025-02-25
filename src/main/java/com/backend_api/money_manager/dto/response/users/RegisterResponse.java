package com.backend_api.money_manager.dto.response.users;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterResponse {
    private String email;
    private String token;
}

package com.backend_api.money_manager.dto.response.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class SignResponse {
    private String email;
    private String role;
    private String token;
    private String refreshToken;
}

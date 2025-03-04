package com.backend_api.money_manager.dto.request.users;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterRequest {
    private String fullName;
    private String email;
    private String phoneNumber;
    private String password;
}

package com.backend_api.money_manager.dto.response.users;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InfoBalance {
    private String value;
    private String updatedAt;
    private String fullName;
}

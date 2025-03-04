package com.backend_api.money_manager.dto.response.subscription;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryTransactionResponse {
    private String id;
    private String name;
    private String description;
}

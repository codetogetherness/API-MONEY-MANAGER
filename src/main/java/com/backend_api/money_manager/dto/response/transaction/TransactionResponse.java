package com.backend_api.money_manager.dto.response.transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor

public class TransactionResponse {
    private String amount;
    private String categoryId;
    private String categoryAction;
    private String image;
    private String description;
    private String title;
}

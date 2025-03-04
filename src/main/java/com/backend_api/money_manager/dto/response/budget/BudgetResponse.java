package com.backend_api.money_manager.dto.response.budget;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BudgetResponse {
    private String amount;
    private String categoryId;
}

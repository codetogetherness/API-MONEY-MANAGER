package com.backend_api.money_manager.dto.request.budget;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BudgetRequest {
    private String amount;
    private String categoryId;
}

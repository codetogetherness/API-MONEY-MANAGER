package com.backend_api.money_manager.dto.request.budget;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BudgetIdRequest {
    private String id;
    private String amount;
    private String categoryId;
}

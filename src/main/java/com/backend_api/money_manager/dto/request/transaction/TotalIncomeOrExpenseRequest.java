package com.backend_api.money_manager.dto.request.transaction;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TotalIncomeOrExpenseRequest {
    private String actionId;
    private String startDate;
    private String endDate;
}

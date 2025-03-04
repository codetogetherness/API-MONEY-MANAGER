package com.backend_api.money_manager.dto.response.transaction;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class IncomeOrExpenseResponse {
    private String name;
    private String total;
}

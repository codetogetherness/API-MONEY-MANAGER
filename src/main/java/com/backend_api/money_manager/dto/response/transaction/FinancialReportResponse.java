package com.backend_api.money_manager.dto.response.transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class FinancialReportResponse {
    private String categoryName;
    private String total;
    private String total_all;
}

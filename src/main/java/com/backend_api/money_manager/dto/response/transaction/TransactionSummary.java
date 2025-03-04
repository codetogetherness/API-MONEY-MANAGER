package com.backend_api.money_manager.dto.response.transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class TransactionSummary {
    private String initialDate;
    private String total;
}

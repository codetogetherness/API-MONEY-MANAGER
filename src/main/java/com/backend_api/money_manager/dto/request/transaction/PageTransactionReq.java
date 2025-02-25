package com.backend_api.money_manager.dto.request.transaction;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PageTransactionReq {
    private int offset;
    private int limit;
    private String startDate;
    private String endDate;
}

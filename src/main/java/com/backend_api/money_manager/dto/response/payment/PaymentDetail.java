package com.backend_api.money_manager.dto.response.payment;

import com.backend_api.money_manager.entity.Payment;
import com.backend_api.money_manager.entity.PaymentCategory;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaymentDetail {
    private String id;
    private String numberCode;
    private Payment payment;
    private String total;
    private String adminFee;
    private String amount;
    private String status;
    private String createdAt;
    private String expired;
}

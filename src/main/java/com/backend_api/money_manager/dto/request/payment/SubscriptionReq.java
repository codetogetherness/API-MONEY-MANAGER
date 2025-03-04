package com.backend_api.money_manager.dto.request.payment;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SubscriptionReq {
    String subscriptionId;
    String paymentMethod;
    String paymentCategory;
}

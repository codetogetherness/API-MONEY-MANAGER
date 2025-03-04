package com.backend_api.money_manager.service.payment;

import com.backend_api.money_manager.dto.request.payment.PaymentVaRequest;
import com.backend_api.money_manager.dto.request.payment.SubscriptionReq;
import org.springframework.http.ResponseEntity;

public interface PaymentService {
    ResponseEntity<Object> createPayment(SubscriptionReq request);

    ResponseEntity<Object> listPayment();
    ResponseEntity<Object> detailPayment();
    ResponseEntity<Object> activeTransction();
}

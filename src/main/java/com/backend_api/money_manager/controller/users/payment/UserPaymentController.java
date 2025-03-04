package com.backend_api.money_manager.controller.users.payment;


import com.backend_api.money_manager.dto.request.payment.PaymentVaRequest;
import com.backend_api.money_manager.dto.request.payment.SubscriptionReq;
import com.backend_api.money_manager.service.payment.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserPaymentController {

    @Autowired
    PaymentService paymentService;

    @PostMapping("/payment")
    public ResponseEntity<Object> createPayment(@RequestBody SubscriptionReq request) {
        return paymentService.createPayment(request);
    }

    @GetMapping("/payment-method")
    public ResponseEntity<Object> listPayment() {
        return paymentService.listPayment();
    }

    @GetMapping("/payment-detail")
    public ResponseEntity<Object> paymentDetail() {
        return paymentService.detailPayment();
    }

}

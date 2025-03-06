package com.backend_api.money_manager.controller.users.payment;


import com.backend_api.money_manager.dto.request.payment.PaymentVaRequest;
import com.backend_api.money_manager.dto.request.payment.SubscriptionReq;
import com.backend_api.money_manager.service.payment.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserPaymentController {

    @Autowired
    PaymentService paymentService;

    @Operation(
            summary = "Secure data",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @PostMapping("/payment")
    public ResponseEntity<Object> createPayment(@RequestBody SubscriptionReq request) {
        return paymentService.createPayment(request);
    }

    @Operation(
            summary = "Secure data",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @GetMapping("/payment-method")
    public ResponseEntity<Object> listPayment() {
        return paymentService.listPayment();
    }

    @Operation(
            summary = "Secure data",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @GetMapping("/payment-detail")
    public ResponseEntity<Object> paymentDetail() {
        return paymentService.detailPayment();
    }

}

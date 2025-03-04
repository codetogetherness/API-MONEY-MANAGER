package com.backend_api.money_manager.dto.response.payment;

import com.backend_api.money_manager.entity.Payment;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Setter
@Getter
public class PaymentMethodResponse {
    private List<Payment> virtualAccount;
    private List<Payment> eWallet;
}

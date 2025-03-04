package com.backend_api.money_manager.dto.response.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WebhookVaResponse {

    @JsonProperty("va_numbers")
    private List<VaNumber> vaNumbers;

    @JsonProperty("transaction_time")
    private String transactionTime;

    @JsonProperty("transaction_status")
    private String transactionStatus;

    @JsonProperty("transaction_id")
    private String transactionId;

    @JsonProperty("status_message")
    private String statusMessage;

    @JsonProperty("status_code")
    private String statusCode;

    @JsonProperty("signature_key")
    private String signatureKey;

    @JsonProperty("settlement_time")
    private String settlementTime;

    @JsonProperty("payment_type")
    private String paymentType;

    @JsonProperty("payment_amounts")
    private List<PaymentAmount> paymentAmounts;

    @JsonProperty("order_id")
    private String orderId;

    @JsonProperty("merchant_id")
    private String merchantId;

    @JsonProperty("gross_amount")
    private String grossAmount;

    @JsonProperty("fraud_status")
    private String fraudStatus;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("expiry_time")
    private String expiryTime;

    @JsonProperty("acquirer")
    private String acquirer;

    // Inner class for VaNumber
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class VaNumber {
        @JsonProperty("va_number")
        private String vaNumber;

        @JsonProperty("bank")
        private String bank;
    }

    // Inner class for PaymentAmount
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PaymentAmount {
        @JsonProperty("paid_at")
        private String paidAt;

        @JsonProperty("amount")
        private String amount;
    }
}

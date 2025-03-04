package com.backend_api.money_manager.dto.response.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaymentResponse {
    @JsonProperty("token")
    private String token;

    @JsonProperty("redirect_url")
    private String redirectUrl;
}

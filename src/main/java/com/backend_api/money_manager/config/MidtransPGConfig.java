package com.backend_api.money_manager.config;

import com.midtrans.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MidtransPGConfig {

    @Bean
    public Config midtransConfig() {
        Config config = Config.builder()
                .setServerKey("SB-Mid-server-yfbU1bC_wVS8RML2LAQD9gHV")
                .setClientKey("SB-Mid-client-_vgv43K6mZmk0PCm")
                .setIsProduction(false)
                .setPaymentAppendNotification("https://app.sandbox.midtrans.com/snap/v1/transactions")
                .build();
        return config;
    }
}

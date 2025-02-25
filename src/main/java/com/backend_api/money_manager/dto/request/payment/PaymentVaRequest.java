package com.backend_api.money_manager.dto.request.payment;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PaymentVaRequest {

    private TransactionDetails transaction_details;
    private List<ItemDetails> item_details;
    private CustomerDetails customer_details;
    private List<String> enabled_payments;
    private NumberVa numberVa;
    private String subscriptionId;
    private Shopper shopper;


    @Setter
    @Getter
    public static class TransactionDetails {
        private String order_id;
        private Integer gross_amount;

    }
    @Setter
    @Getter
    public static class ItemDetails {
        private String id;
        private Integer price;
        private Integer quantity;
        private String name;
        private String brand;
        private String category;
        private String merchant_name;

    }
    @Setter
    @Getter
    public static class CustomerDetails {
        private String first_name;
        private String last_name;
        private String email;
        private String phone;

    }
    @Setter
    @Getter
    public static class NumberVa {
        private String va_number;
        private String sub_company_code;
        private FreeText free_text;

    }
    @Setter
    @Getter
    public static class FreeText {
        private List<Text> inquiry;
        private List<Text> payment;
    }

    @Setter
    @Getter
    public static class Text {
        private String en;
        private String id;

    }

    @Setter
    @Getter
    public static class Shopper {
        private String callback_shopee_url;
    }
}

package com.backend_api.money_manager.dto.response.transaction;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ListTransactionResponse {
    private String id;
    private String title;
    private String description;
    private String total;
    private User user;
    private Category category;
    private String createdAt;
    private String typeAction;

    @Getter
    @Setter
    public static class User{
        private String id;
        private String fullName;
        private String email;
    }

    @Getter
    @Setter
    public static class Category{
        private String id;
        private String name;
        private String icon;
    }


}

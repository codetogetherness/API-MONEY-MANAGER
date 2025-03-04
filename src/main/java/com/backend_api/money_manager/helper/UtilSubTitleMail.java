package com.backend_api.money_manager.helper;

public class UtilSubTitleMail {
    public static String activationLink(String baseUrl, String token) {
        return baseUrl + "/activate?token=" + token;
    }
}

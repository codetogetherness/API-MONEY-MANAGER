package com.backend_api.money_manager.helper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class GenerateCode {
    public static String code() {
        Random rand = new Random();

        String prefix = "ALLT";
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String dateNow = LocalDateTime.now().format(dateFormatter);
        int randomNumber = rand.nextInt(10000);

        String formattedNumber = String.format("%04d", randomNumber);

        String randomString = prefix + formattedNumber + dateNow;

        return randomString;
    }
}

package com.backend_api.money_manager.helper;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

@Component
public class MailHelper {

    @Autowired
    private JavaMailSender emailSender;

    @SneakyThrows
    public void sendEmail(String to, String subject, String htmlBody, int retryCount) {
        Thread emailThread = new Thread(() -> {
           try {
               MimeMessage message = emailSender.createMimeMessage();
               String from = "lordgentro@gmail.com";
               message.setFrom("EMAIL DARI" + "<" + from + ">");

               message.setSubject(subject);
               message.setContent(htmlBody, "text/html; charset=utf-8");
               message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(to));
               emailSender.send(message);
           } catch (MessagingException e) {
               if (retryCount > 0) {
                   sendEmail(to, subject, htmlBody, retryCount - 1);
               } else {
                   System.out.println("Failed to send email after 3 attempts");
               }
           }
        });
        emailThread.start();
    }

    public int generateOTP() {
        Random random = new Random();
        int otp  = 1000 + random.nextInt(9000);
        return Integer.parseInt(String.valueOf(otp));
    }
}

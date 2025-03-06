package com.backend_api.money_manager.controller.webhook;

import com.backend_api.money_manager.dto.response.payment.WebhookVaResponse;
import com.backend_api.money_manager.entity.StatusTransaction;
import com.backend_api.money_manager.entity.TransactionUser;
import com.backend_api.money_manager.exception.ResponseHandler;
import com.backend_api.money_manager.repository.TransactionUserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/webhook")
public class WebhookController {

    @Autowired
    TransactionUserRepository transactionUserRepository;

    @Operation(
            summary = "Secure data",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @PostMapping("/callback-transaction")
    public ResponseEntity<Object> handlePaymentWebhook(@RequestBody WebhookVaResponse webhookResponse) {
        TransactionUser find = transactionUserRepository.findByCode(webhookResponse.getOrderId());

        if(find != null) {
            if(Objects.equals(webhookResponse.getPaymentType(), "qris")){
                // code shopeepay
                if (Objects.equals(webhookResponse.getAcquirer(),"airpay shopee")) {
                    if(Objects.equals(webhookResponse.getTransactionStatus(), "settlement")) {
                        find.setActive(true);
                        find.setStatusTransaction(StatusTransaction.SUCCESS);
                        if (Objects.equals(find.getCategoryTransaction().getName(), "Paket 3 bulan")) {
                            LocalDate today = LocalDate.now();
                            DateTimeFormatter.ofPattern("dd-MM-yyyy");
                            find.setStartDate(today);

                            LocalDate endOfThreeMonths = today.plusMonths(3);
                            find.setEndDate(endOfThreeMonths);
                        } else if (Objects.equals(find.getCategoryTransaction().getName(), "Paket 6 bulan")) {
                            LocalDate today = LocalDate.now();
                            DateTimeFormatter.ofPattern("dd-MM-yyyy");
                            find.setStartDate(today);

                            LocalDate endOfThreeMonths = today.plusMonths(6);
                            find.setEndDate(endOfThreeMonths);
                        } else if (Objects.equals(find.getCategoryTransaction().getName(), "Paket 12 bulan")) {
                            LocalDate today = LocalDate.now();
                            DateTimeFormatter.ofPattern("dd-MM-yyyy");
                            find.setStartDate(today);
                            LocalDate endOfThreeMonths = today.plusMonths(12);
                            find.setEndDate(endOfThreeMonths);
                        }
                    } else if(Objects.equals(webhookResponse.getTransactionStatus(), "cancel")) {
                        find.setStatusTransaction(StatusTransaction.CANCEL);
                    } else if(Objects.equals(webhookResponse.getTransactionStatus(), "expire")){
                        find.setStatusTransaction(StatusTransaction.EXPIRED);
                    }else if(Objects.equals(webhookResponse.getTransactionStatus(), "deny")){
                        find.setStatusTransaction(StatusTransaction.DENY);
                    }

//                    find.setNumberCode(webhookResponse.getVaNumbers().get(0).getVaNumber());
                    find.setExpired(webhookResponse.getExpiryTime());

                    transactionUserRepository.save(find);
                }

            } else if(Objects.equals(webhookResponse.getPaymentType(), "bank_transfer")){
                if(Objects.equals(webhookResponse.getTransactionStatus(), "settlement")) {
                    find.setActive(true);
                    find.setStatusTransaction(StatusTransaction.SUCCESS);
                    if (Objects.equals(find.getCategoryTransaction().getName(), "Paket 3 bulan")) {
                        LocalDate today = LocalDate.now();
                        DateTimeFormatter.ofPattern("dd-MM-yyyy");
                        find.setStartDate(today);

                        LocalDate endOfThreeMonths = today.plusMonths(3);
                        find.setEndDate(endOfThreeMonths);
                    } else if (Objects.equals(find.getCategoryTransaction().getName(), "Paket 6 bulan")) {
                        LocalDate today = LocalDate.now();
                        DateTimeFormatter.ofPattern("dd-MM-yyyy");
                        find.setStartDate(today);

                        LocalDate endOfThreeMonths = today.plusMonths(6);
                        find.setEndDate(endOfThreeMonths);
                    } else if (Objects.equals(find.getCategoryTransaction().getName(), "Paket 12 bulan")) {
                        LocalDate today = LocalDate.now();
                        DateTimeFormatter.ofPattern("dd-MM-yyyy");
                        find.setStartDate(today);
                        LocalDate endOfThreeMonths = today.plusMonths(12);
                        find.setEndDate(endOfThreeMonths);
                    }
                } else if(Objects.equals(webhookResponse.getTransactionStatus(), "cancel")) {
                    find.setStatusTransaction(StatusTransaction.CANCEL);
                } else if(Objects.equals(webhookResponse.getTransactionStatus(), "expire")){
                    find.setStatusTransaction(StatusTransaction.EXPIRED);
                }else if(Objects.equals(webhookResponse.getTransactionStatus(), "deny")){
                    find.setStatusTransaction(StatusTransaction.DENY);
                }

                find.setNumberCode(webhookResponse.getVaNumbers().get(0).getVaNumber());
                find.setExpired(webhookResponse.getExpiryTime());

                transactionUserRepository.save(find);
            }


            return ResponseHandler.generateResponseSuccess(true);

        }
        return ResponseHandler.generateResponseSuccess(false);

    }

    @Operation(
            summary = "Secure data",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @PostMapping("/payment-finish")
    public ResponseEntity<Object> handlePaymentWebhookSuccess(@RequestBody WebhookVaResponse webhookResponse) {
        return ResponseHandler.generateResponseSuccess(true);
    }

}

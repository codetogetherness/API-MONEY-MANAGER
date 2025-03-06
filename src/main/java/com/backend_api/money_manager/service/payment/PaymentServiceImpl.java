package com.backend_api.money_manager.service.payment;

import com.backend_api.money_manager.dto.request.payment.DynamicRequest;
import com.backend_api.money_manager.dto.request.payment.PaymentVaRequest;
import com.backend_api.money_manager.dto.request.payment.SubscriptionReq;
import com.backend_api.money_manager.dto.response.payment.PaymentDetail;
import com.backend_api.money_manager.dto.response.payment.PaymentMethodResponse;
import com.backend_api.money_manager.dto.response.payment.PaymentResponse;
import com.backend_api.money_manager.entity.Payment;
import com.backend_api.money_manager.entity.StatusTransaction;
import com.backend_api.money_manager.entity.TransactionUser;
import com.backend_api.money_manager.exception.ResponseHandler;
import com.backend_api.money_manager.helper.GenerateCode;
import com.backend_api.money_manager.helper.InfoAccount;
import com.backend_api.money_manager.repository.CategoryTransactionRepository;
import com.backend_api.money_manager.repository.PaymentRepository;
import com.backend_api.money_manager.repository.TransactionUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    InfoAccount infoAccount;

    @Autowired
    PaymentRepository paymentRepository;

    @Value("${mid.secret.key}")
    private String midSecretKey;

    @Value("${mid.uri.sandbox}")
    private String midUrl;

    @Autowired
    TransactionUserRepository transactionUserRepository;

    @Autowired
    CategoryTransactionRepository categoryTransactionRepository;



    @Override
    public ResponseEntity<Object> createPayment(SubscriptionReq request) {

        var findCat = categoryTransactionRepository.findById(request.getSubscriptionId());
//        if(request.getPaymentMethod() == "Shopeepay"){

//        }
        String code = GenerateCode.code();
        String apiUrl = midUrl;
        String password = "";
        String auth = midSecretKey + ":" + password;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
        int adminFee = 6500;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Basic " + encodedAuth);

        if("shopeepay".equalsIgnoreCase(request.getPaymentCategory())){
//            return  null;
            PaymentVaRequest pay = new PaymentVaRequest();
            int total = Integer.parseInt(findCat.get().getAmount()) + adminFee;

            PaymentVaRequest.TransactionDetails transactionDetails  = new PaymentVaRequest.TransactionDetails();

            transactionDetails.setOrder_id(code);
            transactionDetails.setGross_amount(total);

            pay.setTransaction_details(transactionDetails);
            //       ======= Item =====
            PaymentVaRequest.ItemDetails item = new PaymentVaRequest.ItemDetails();
            item.setName(findCat.get().getName());
            item.setPrice(total);
            item.setCategory(findCat.get().getName());
            item.setQuantity(1);
            item.setId(findCat.get().getId());
            item.setBrand("Money Manager");

            List<PaymentVaRequest.ItemDetails> listItem = new ArrayList<>();

            listItem.add(item);
            pay.setItem_details(listItem);

            //  ====== Customer Detail ========
            PaymentVaRequest.CustomerDetails customer = new PaymentVaRequest.CustomerDetails();

            customer.setEmail(infoAccount.get().getEmail());
            customer.setFirst_name(infoAccount.get().getFullName());
            customer.setPhone(infoAccount.get().getPhoneNumber());

            pay.setCustomer_details(customer);

            List<String> va = new ArrayList<>();
            va.add(request.getPaymentMethod());

            pay.setEnabled_payments(va);

//            Map<String, Object> dynamicData = new HashMap<>();
//
//            Map<String, String> typeVa = new HashMap<>();
//            typeVa.put("va_number", "12345678");
//            dynamicData.put(request.getPaymentMethod(), typeVa);

            // Handling ewallet shopee pay
            PaymentVaRequest.Shopper shopperurl = new PaymentVaRequest.Shopper();
            shopperurl.setCallback_shopee_url("https://shopee.co.id/buyer/login");
            pay.setShopper(shopperurl);

            HttpEntity<PaymentVaRequest> entity = new HttpEntity<>(pay, headers);
            TransactionUser body = new TransactionUser();

            if(findCat != null){
                body.setCategoryTransaction(findCat.get());
            }

            body.setId(UUID.randomUUID().toString());
            body.setActive(false);
            body.setTotalAmount(String.valueOf(total));
            body.setAdminFee(String.valueOf(adminFee));
            body.setAmount(findCat.get().getAmount());
            body.setCode(code);
            body.setUser(infoAccount.get());
            body.setStatusTransaction(StatusTransaction.PENDING);
            Payment payment = paymentRepository.findByCode(request.getPaymentMethod());
            body.setPayment(payment);

            transactionUserRepository.save(body);

            ResponseEntity<PaymentResponse> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, PaymentResponse.class);
            return ResponseHandler.generateResponseSuccess(response);
        }

        else if(Objects.equals(request.getPaymentCategory(), "bank_transfer")){
            PaymentVaRequest pay = new PaymentVaRequest();
            int total = Integer.parseInt(findCat.get().getAmount()) + adminFee;

            PaymentVaRequest.TransactionDetails transactionDetails  = new PaymentVaRequest.TransactionDetails();

            transactionDetails.setOrder_id(code);
            transactionDetails.setGross_amount(total);

            pay.setTransaction_details(transactionDetails);
//       ======= Item =====
            PaymentVaRequest.ItemDetails item = new PaymentVaRequest.ItemDetails();
            item.setName(findCat.get().getName());
            item.setPrice(total);
            item.setCategory(findCat.get().getName());
            item.setQuantity(1);
            item.setId(findCat.get().getId());
            item.setBrand("Money Manager");

            List<PaymentVaRequest.ItemDetails> listItem = new ArrayList<>();

            listItem.add(item);
            pay.setItem_details(listItem);

            //  ====== Customer Detail ========
            PaymentVaRequest.CustomerDetails customer = new PaymentVaRequest.CustomerDetails();

            customer.setEmail(infoAccount.get().getEmail());
            customer.setFirst_name(infoAccount.get().getFullName());
            customer.setPhone(infoAccount.get().getPhoneNumber());

            pay.setCustomer_details(customer);

            List<String> va = new ArrayList<>();
            va.add(request.getPaymentMethod());

            pay.setEnabled_payments(va);

            Map<String, Object> dynamicData = new HashMap<>();

            Map<String, String> typeVa = new HashMap<>();
            typeVa.put("va_number", "12345678");
            dynamicData.put(request.getPaymentMethod(), typeVa);

            // Handling dynamic NumberVa
            PaymentVaRequest.NumberVa number = new PaymentVaRequest.NumberVa();
            number.setVa_number("12345678");
            number.setSub_company_code(code);
            number.setFree_text(new PaymentVaRequest.FreeText());

            pay.setNumberVa(number);

            HttpEntity<PaymentVaRequest> entity = new HttpEntity<>(pay, headers);
            TransactionUser body = new TransactionUser();

            if(findCat != null){
                body.setCategoryTransaction(findCat.get());
            }

            body.setId(UUID.randomUUID().toString());
            body.setActive(false);
            body.setTotalAmount(String.valueOf(total));
            body.setAdminFee(String.valueOf(adminFee));
            body.setAmount(findCat.get().getAmount());
            body.setCode(code);
            body.setUser(infoAccount.get());
            body.setStatusTransaction(StatusTransaction.PENDING);
            Payment payment = paymentRepository.findByCode(request.getPaymentMethod());
            body.setPayment(payment);

            transactionUserRepository.save(body);

            ResponseEntity<PaymentResponse> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, PaymentResponse.class);
            return ResponseHandler.generateResponseSuccess(response);

        }

        return ResponseHandler.generateResponseSuccess(null);
//        return ResponseHandler.generateResponseSuccess("okeeee");
    }

    @Override
    public ResponseEntity<Object> listPayment() {
        var response = paymentRepository.findAll();

        PaymentMethodResponse paymentMethodResponse = new PaymentMethodResponse();
        Map<String, List<Payment>> groupedByCategory = response.stream()
                .collect(Collectors.groupingBy(payment -> payment.getPaymentCategory().getName()));

        for (Map.Entry<String, List<Payment>> entry : groupedByCategory.entrySet()) {
            String categoryName = entry.getKey();
            List<Payment> paymentsInCategory = entry.getValue();

            if ("Virtual Account".equals(categoryName)) {
                paymentMethodResponse.setVirtualAccount(paymentsInCategory);
            }

        }

        return ResponseHandler.generateResponseSuccess(paymentMethodResponse);
    }

    @Override
    public ResponseEntity<Object> detailPayment() {
        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = now.format(formatter);
        TransactionUser transactionUser = transactionUserRepository.findTransactionNow(infoAccount.get().getId(),formattedDate);
        if (transactionUser == null) {
            return ResponseHandler.generateResponseError(HttpStatus.NOT_FOUND,"","Transaction Not Found");
        }
        PaymentDetail paymentDetail = new PaymentDetail();
        paymentDetail.setId(transactionUser.getId());
        paymentDetail.setTotal(transactionUser.getTotalAmount());
        paymentDetail.setPayment(transactionUser.getPayment());
        paymentDetail.setAmount(transactionUser.getAmount());
        paymentDetail.setNumberCode(transactionUser.getNumberCode());
        paymentDetail.setCreatedAt(transactionUser.getCreatedAt().toString());
        paymentDetail.setStatus(transactionUser.getStatusTransaction().name());
        paymentDetail.setAdminFee(transactionUser.getAdminFee());

        return ResponseHandler.generateResponseSuccess(paymentDetail);
    }

    @Override
    public ResponseEntity<Object> activeTransction() {

        return null;
    }


}

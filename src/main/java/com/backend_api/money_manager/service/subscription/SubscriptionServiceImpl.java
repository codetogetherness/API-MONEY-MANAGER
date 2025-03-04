package com.backend_api.money_manager.service.subscription;

import com.backend_api.money_manager.entity.CategoryTransaction;
import com.backend_api.money_manager.entity.TransactionUser;
import com.backend_api.money_manager.exception.ResponseHandler;
import com.backend_api.money_manager.helper.InfoAccount;
import com.backend_api.money_manager.repository.CategoryTransactionRepository;
import com.backend_api.money_manager.repository.TransactionUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    CategoryTransactionRepository categoryTransactionRepository;

    @Autowired
    TransactionUserRepository transactionUserRepository;

    @Autowired
    InfoAccount infoAccount;

    @Override
    public ResponseEntity<Object> getListSubscription() {
        try{

            List<CategoryTransaction> response = categoryTransactionRepository.findAll();
            response = response.stream()
                    .sorted((a, b) -> {
                        try {
                            int amountA = Integer.parseInt(a.getAmount());
                            int amountB = Integer.parseInt(b.getAmount());
                            return Integer.compare(amountA, amountB);
                        } catch (NumberFormatException e) {
                            return 0;
                        }
                    })
                    .collect(Collectors.toList());
            return ResponseHandler.generateResponseSuccess(response);
        }catch (Exception e){
            return ResponseHandler.generateResponseError(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),e.getMessage());
        }


    }

    @Override
    public ResponseEntity<Object> getCekSubscription() {
        Optional<TransactionUser> find = transactionUserRepository.findLatestTransactionUserByUserId(infoAccount.get().getId());

        if (find.isPresent()) {
            return ResponseHandler.generateResponseSuccess(true);
        }
        return ResponseHandler.generateResponseSuccess(false);
    }

}

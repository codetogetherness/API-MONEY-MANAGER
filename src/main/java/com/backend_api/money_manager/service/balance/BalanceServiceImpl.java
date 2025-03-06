package com.backend_api.money_manager.service.balance;

import com.backend_api.money_manager.dto.response.users.InfoBalance;
import com.backend_api.money_manager.entity.BalanceUser;
import com.backend_api.money_manager.exception.ResponseHandler;
import com.backend_api.money_manager.helper.InfoAccount;
import com.backend_api.money_manager.repository.BalanceUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BalanceServiceImpl implements BalanceService {


    @Autowired
    BalanceUserRepository balanceUserRepository;

    @Autowired
    InfoAccount infoAccount;

    @Override
    public ResponseEntity<Object> getBalanceUser() {
        try{

            BalanceUser response = balanceUserRepository.findByUserId(infoAccount.get().getId());
            InfoBalance balance = new InfoBalance();
            balance.setFullName(response.getUser().getFullName());
            balance.setValue(response.getValue());
            balance.setUpdatedAt(response.getUpdatedAt().toString());

            return ResponseHandler.generateResponseSuccess(balance);

        }catch (Exception e){
            return ResponseHandler.generateResponseError(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),"");
        }

    }

}

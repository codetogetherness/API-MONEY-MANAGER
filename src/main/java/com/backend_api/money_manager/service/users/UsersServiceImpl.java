package com.backend_api.money_manager.service.users;

import com.backend_api.money_manager.dto.request.users.AccountVerification;
import com.backend_api.money_manager.entity.AccountStatus;
import com.backend_api.money_manager.entity.LogVerification;
import com.backend_api.money_manager.exception.ResponseHandler;
import com.backend_api.money_manager.helper.InfoAccount;
import com.backend_api.money_manager.repository.LogVerificationRepository;
import com.backend_api.money_manager.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService{

    @Autowired
    InfoAccount infoAccount;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    LogVerificationRepository logVerificationRepository;

    @Override
    public ResponseEntity<Object> verificationAccount(AccountVerification request) {

        String user = infoAccount.get().getEmail();

        var data = usersRepository.findByEmail(user);

        data.get().setAccountStatus(AccountStatus.VERIFIED);


        return ResponseHandler.generateResponseSuccess(user);
    }

}

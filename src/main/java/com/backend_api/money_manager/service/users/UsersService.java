package com.backend_api.money_manager.service.users;

import com.backend_api.money_manager.dto.request.users.AccountVerification;
import org.springframework.http.ResponseEntity;

public interface UsersService {
    ResponseEntity<Object> verificationAccount(AccountVerification request);

}

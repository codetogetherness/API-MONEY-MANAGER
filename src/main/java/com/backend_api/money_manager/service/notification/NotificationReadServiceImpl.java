package com.backend_api.money_manager.service.notification;

import com.backend_api.money_manager.dto.request.notification.NotificationReadRequest;
import com.backend_api.money_manager.entity.BalanceUser;
import com.backend_api.money_manager.entity.NotificationRead;
import com.backend_api.money_manager.exception.ResponseHandler;
import com.backend_api.money_manager.helper.InfoAccount;
import com.backend_api.money_manager.repository.BalanceUserRepository;
import com.backend_api.money_manager.repository.NotificationReadRepository;
import com.backend_api.money_manager.repository.NotificationRepository;
import com.backend_api.money_manager.repository.UsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
public class NotificationReadServiceImpl implements NotificationReadService{

    @Autowired
    BalanceUserRepository balanceUserRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    NotificationReadRepository notificationReadRepository;

    @Autowired
    InfoAccount infoAccount;

    @Override
    public ResponseEntity<Object> create(NotificationReadRequest request) {
        try {
            String user = infoAccount.get().getEmail();
            var notification = notificationRepository.findById(request.getNotificationId()).orElseThrow();
            var findNotificationRead = notificationReadRepository.findByNotificationId(request.getNotificationId());


            if (notification == null) {
                return ResponseHandler.generateResponseError(HttpStatus.BAD_REQUEST,"","Info Notification Not Found");
            }

            var find = usersRepository.findByEmail(user).orElseThrow();

            findNotificationRead.setIsRead(true);

            var data = notificationReadRepository.save(findNotificationRead);

            return ResponseHandler.generateResponseSuccess(data.getIsRead());

        } catch (Exception e) {
            return ResponseHandler.generateResponseError(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),"");
        }
    }
}

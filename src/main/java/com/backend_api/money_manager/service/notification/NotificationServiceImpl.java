package com.backend_api.money_manager.service.notification;

import com.backend_api.money_manager.dto.request.notification.NotificationRequest;
import com.backend_api.money_manager.dto.request.notification.NotificationRequestUser;
import com.backend_api.money_manager.dto.request.notification.PageNotificationRequest;
import com.backend_api.money_manager.dto.response.notification.NotificationResponse;
import com.backend_api.money_manager.entity.AccountStatus;
import com.backend_api.money_manager.entity.BalanceUser;
import com.backend_api.money_manager.entity.Notification;
import com.backend_api.money_manager.entity.Users;
import com.backend_api.money_manager.exception.ResponseHandler;
import com.backend_api.money_manager.exception.ResponseHandlerWithPagination;
import com.backend_api.money_manager.helper.InfoAccount;
import com.backend_api.money_manager.repository.BalanceUserRepository;
import com.backend_api.money_manager.repository.NotificationRepository;
import com.backend_api.money_manager.repository.UsersRepository;
import com.backend_api.money_manager.service.transaction_history.TransactionUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService{

    @Autowired
    BalanceUserRepository balanceUserRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    InfoAccount infoAccount;

    @Autowired
    NotificationUtil notificationUtil;

    @Autowired
    TransactionUtil transactionUtil;

    @Override
    public ResponseEntity<Object> create(NotificationRequest request) {
        try {
            String user = infoAccount.get().getEmail();

            BalanceUser balance = balanceUserRepository.findByUserId(infoAccount.get().getId());

            if(balance == null){
                return ResponseHandler.generateResponseError(HttpStatus.BAD_REQUEST,"","Info Balance Not Found");
            }

//            Users data = usersRepository.findByEmail(user).orElseThrow();
//            data.setAccountStatus(AccountStatus.VERIFIED);
//            var save = usersRepository.save(data);
            var find = usersRepository.findByEmail(user).orElseThrow();

            Notification notification = new Notification();
            notification.setId(UUID.randomUUID().toString());
            notification.setTitle(request.getTitle());
            notification.setDetail(request.getDetail());
            notification.setUser(find);
            var data = notificationRepository.save(notification);

            return ResponseHandler.generateResponseSuccess(data);

        } catch (Exception e) {
            return ResponseHandler.generateResponseError(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),"");
        }
    }

    @Override
    public ResponseEntity<Object> findUser(PageNotificationRequest request) {
//        String user = infoAccount.get().getId();
//        var data = notificationRepository.getByUser(user);
//        return ResponseHandler.generateResponseSuccess(data);
        List<NotificationResponse> data = notificationUtil.getListUserNotification(request);
        int totalPage = notificationRepository.countDataByUser(infoAccount.get().getId());

        ResponseHandlerWithPagination.InfoPage pages = new ResponseHandlerWithPagination.InfoPage();
        pages.setTotalPage(totalPage);
        pages.setOffset(request.getOffset());
        pages.setLimit(request.getLimit());
        pages.setCurrentPage(request.getOffset());

        ResponseHandlerWithPagination.Data response = new ResponseHandlerWithPagination.Data();
        response.setPage(pages);
        response.setData(data);

        return ResponseHandlerWithPagination.generateResponseSuccess(response);
    }
}

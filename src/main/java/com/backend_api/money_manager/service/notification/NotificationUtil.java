package com.backend_api.money_manager.service.notification;

import com.backend_api.money_manager.dto.request.notification.PageNotificationRequest;
import com.backend_api.money_manager.dto.response.notification.NotificationResponse;
import com.backend_api.money_manager.entity.Notification;
import com.backend_api.money_manager.entity.NotificationRead;
import com.backend_api.money_manager.helper.InfoAccount;
import com.backend_api.money_manager.helper.StorageUtil;
import com.backend_api.money_manager.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationUtil {

    @Autowired
    StorageUtil storageUtil;

    @Autowired
    InfoAccount infoAccount;

    @Autowired
    NotificationRepository notificationRepository;

    public List<NotificationResponse> getListUserNotification(PageNotificationRequest request) {

        Pageable page = PageRequest.of(request.getOffset(), request.getLimit());

        List<NotificationRead> res = notificationRepository.findNotificationsForUser(infoAccount.get().getId(), page); //kayaknya dirombak

        List<NotificationResponse> notifications = new ArrayList<>();

        for (NotificationRead re : res) {
            NotificationResponse obj = new NotificationResponse();
            obj.setId(re.getNotification().getId());
            obj.setDetail(re.getNotification().getDetail());
            obj.setTitle(re.getNotification().getTitle());
            obj.setCreatedAt(re.getCreatedAt().toString());
            obj.setRead(re.getIsRead());

            notifications.add(obj);
        }

        return notifications;
    }
}

package com.backend_api.money_manager.dto.request.notification;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NotificationRequest {
    private String title;

    private String detail;

}

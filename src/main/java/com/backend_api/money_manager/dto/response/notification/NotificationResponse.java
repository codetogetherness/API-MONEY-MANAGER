package com.backend_api.money_manager.dto.response.notification;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NotificationResponse {
    private String id;

    private String title;

    private String detail;

    private String createdAt;
    private boolean isRead;


}

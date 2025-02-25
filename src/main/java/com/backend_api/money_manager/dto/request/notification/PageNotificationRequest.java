package com.backend_api.money_manager.dto.request.notification;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PageNotificationRequest {
    private int offset;
    private int limit;

}

package com.backend_api.money_manager.service.subscription;

import org.springframework.http.ResponseEntity;

public interface SubscriptionService {
    ResponseEntity<Object> getListSubscription();
    ResponseEntity<Object> getCekSubscription();
}

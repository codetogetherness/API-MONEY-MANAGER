package com.backend_api.money_manager.service.budget;

import com.backend_api.money_manager.dto.request.budget.BudgetIdRequest;
import com.backend_api.money_manager.dto.request.budget.BudgetRequest;
import com.backend_api.money_manager.dto.response.budget.BudgetResponse;
import org.springframework.http.ResponseEntity;

public interface BudgetService {
    ResponseEntity<Object> create(BudgetRequest request);
    ResponseEntity<Object> update(BudgetIdRequest request);
    ResponseEntity<Object> getAll(BudgetResponse response);
    ResponseEntity<Object> getId(String id);
    ResponseEntity<Object> delete(BudgetIdRequest request);
}

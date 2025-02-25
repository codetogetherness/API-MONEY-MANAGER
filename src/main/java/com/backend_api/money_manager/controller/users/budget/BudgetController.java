package com.backend_api.money_manager.controller.users.budget;

import com.backend_api.money_manager.dto.request.budget.BudgetIdRequest;
import com.backend_api.money_manager.dto.request.budget.BudgetRequest;
import com.backend_api.money_manager.dto.request.category.CategoryIdRequest;
import com.backend_api.money_manager.dto.request.transaction.TransactionRequest;
import com.backend_api.money_manager.dto.response.budget.BudgetResponse;
import com.backend_api.money_manager.dto.response.category.CategoryResponse;
import com.backend_api.money_manager.service.budget.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class BudgetController {

    @Autowired
    BudgetService budgetService;

    @PostMapping("/budget/create")
    public ResponseEntity<Object> createBudget(@RequestBody BudgetRequest request) {
        return budgetService.create(request);
    }

    @GetMapping("/budget/list")
    public ResponseEntity<Object> getAll(BudgetResponse response) {
        return budgetService.getAll(response);
    }

    @GetMapping("/budget/{id}")
    public ResponseEntity<Object> getId(@PathVariable("id") String id) {
        return budgetService.getId(id);
    }

    @PostMapping("/budget/update")
    public ResponseEntity<Object> updateBudget(@RequestBody BudgetIdRequest request) {
        return budgetService.update(request);
    }

    @PostMapping("/budget/delete")
    public ResponseEntity<Object> softDelete(@RequestBody BudgetIdRequest request) {
        return budgetService.delete(request);
    }
}

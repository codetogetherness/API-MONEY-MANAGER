package com.backend_api.money_manager.controller.users.transaction;

import com.backend_api.money_manager.dto.request.transaction.PageTransactionReq;
import com.backend_api.money_manager.dto.request.transaction.ReportRequest;
import com.backend_api.money_manager.dto.request.transaction.TotalIncomeOrExpenseRequest;
import com.backend_api.money_manager.dto.request.transaction.TransactionRequest;
import com.backend_api.money_manager.service.transaction_history.TransactionHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class TransactionUserController {

    @Autowired
    TransactionHistoryService transactionHistoryService;

    @Operation(
            summary = "Secure data",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @PostMapping("/transaction/history")
    public ResponseEntity<Object> createTransaction(@RequestBody TransactionRequest request) {
        return transactionHistoryService.createTransaction(request);
    }

    @Operation(
            summary = "Secure data",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @PostMapping("/transactions")
    public ResponseEntity<Object> listTransaction(@RequestBody PageTransactionReq request) {
        return transactionHistoryService.getListTransaction(request);
    }

    @Operation(
            summary = "Secure data",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @PostMapping("/income-or-expense")
    public ResponseEntity<Object> totalIncomeOrExpense(@RequestBody TotalIncomeOrExpenseRequest request) {
        return transactionHistoryService.totalIncomeOrExpense(request);
    }

    @Operation(
            summary = "Secure data",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @PostMapping("/report-transaction")
    public ResponseEntity<Object> reportUserTransaction(@RequestBody ReportRequest request) {
        return transactionHistoryService.reportTransactionUser(request);
    }

//  ==========  CHART ================
    @Operation(
        summary = "Secure data",
        security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @PostMapping("/chart-expense")
    public ResponseEntity<Object> expenseChart(@RequestBody ReportRequest request) {
        return transactionHistoryService.userExpenseOrIncomeChartStick(request);
    }


}

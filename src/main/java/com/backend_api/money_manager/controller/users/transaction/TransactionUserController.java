package com.backend_api.money_manager.controller.users.transaction;

import com.backend_api.money_manager.dto.request.transaction.PageTransactionReq;
import com.backend_api.money_manager.dto.request.transaction.ReportRequest;
import com.backend_api.money_manager.dto.request.transaction.TotalIncomeOrExpenseRequest;
import com.backend_api.money_manager.dto.request.transaction.TransactionRequest;
import com.backend_api.money_manager.service.transaction_history.TransactionHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class TransactionUserController {

    @Autowired
    TransactionHistoryService transactionHistoryService;

    @PostMapping("/transaction/history")
    public ResponseEntity<Object> createTransaction(@RequestBody TransactionRequest request) {
        return transactionHistoryService.createTransaction(request);
    }

    @PostMapping("/transactions")
    public ResponseEntity<Object> listTransaction(@RequestBody PageTransactionReq request) {
        return transactionHistoryService.getListTransaction(request);
    }

    @PostMapping("/income-or-expense")
    public ResponseEntity<Object> totalIncomeOrExpense(@RequestBody TotalIncomeOrExpenseRequest request) {
        return transactionHistoryService.totalIncomeOrExpense(request);
    }

    @PostMapping("/report-transaction")
    public ResponseEntity<Object> reportUserTransaction(@RequestBody ReportRequest request) {
        return transactionHistoryService.reportTransactionUser(request);
    }

//  ==========  CHART ================
    @PostMapping("/chart-expense")
    public ResponseEntity<Object> expenseChart(@RequestBody ReportRequest request) {
        return transactionHistoryService.userExpenseOrIncomeChartStick(request);
    }


}

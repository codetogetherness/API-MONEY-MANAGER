package com.backend_api.money_manager.service.transaction_history;

import com.backend_api.money_manager.dto.request.transaction.PageTransactionReq;
import com.backend_api.money_manager.dto.request.transaction.ReportRequest;
import com.backend_api.money_manager.dto.request.transaction.TotalIncomeOrExpenseRequest;
import com.backend_api.money_manager.dto.request.transaction.TransactionRequest;
import org.springframework.http.ResponseEntity;

public interface TransactionHistoryService {
    ResponseEntity<Object> createTransaction(TransactionRequest request);
    ResponseEntity<Object> getListTransaction(PageTransactionReq request);
    ResponseEntity<Object> totalIncomeOrExpense(TotalIncomeOrExpenseRequest request);
    ResponseEntity<Object> getId(String id);
    ResponseEntity<Object> reportTransactionUser(ReportRequest request);
    ResponseEntity<Object> userExpenseOrIncomeChartStick(ReportRequest request);
}

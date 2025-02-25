package com.backend_api.money_manager.service.financial_report;

import com.backend_api.money_manager.dto.request.transaction.FinancialReportRequest;
import org.springframework.http.ResponseEntity;

public interface FinancialReportService {
    ResponseEntity<Object> creteFinancialReport(FinancialReportRequest request);
}

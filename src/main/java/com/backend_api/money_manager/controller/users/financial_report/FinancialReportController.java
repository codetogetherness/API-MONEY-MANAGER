package com.backend_api.money_manager.controller.users.financial_report;

import com.backend_api.money_manager.dto.request.transaction.FinancialReportRequest;
import com.backend_api.money_manager.service.financial_report.FinancialReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class FinancialReportController {

    @Autowired
    FinancialReportService financialReportService;

    @Operation(
            summary = "Secure data",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @PostMapping("/financial/report")
    public ResponseEntity<Object> createFinancial(@RequestBody FinancialReportRequest request) {
        return financialReportService.creteFinancialReport(request);
    }
}

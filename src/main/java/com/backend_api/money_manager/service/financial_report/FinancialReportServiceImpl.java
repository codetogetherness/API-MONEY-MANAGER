package com.backend_api.money_manager.service.financial_report;

import com.backend_api.money_manager.dto.request.transaction.FinancialReportRequest;
import com.backend_api.money_manager.dto.response.transaction.FinancialReportResponse;
import com.backend_api.money_manager.exception.ResponseHandler;
import com.backend_api.money_manager.helper.InfoAccount;
import com.backend_api.money_manager.repository.CategoryActionRepository;
import com.backend_api.money_manager.repository.HistoryTransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class FinancialReportServiceImpl implements FinancialReportService {

    @Autowired
    CategoryActionRepository categoryActionRepository;

    @Autowired
    InfoAccount infoAccount;

    @Autowired
    HistoryTransactionRepository historyTransactionRepository;


    @Override
    public ResponseEntity<Object> creteFinancialReport(FinancialReportRequest request) {
        try {
            List<Object[]> result = historyTransactionRepository.totalAmountUserByMonth(request.getActionId(),request.getStartDate(), request.getEndDate(),
                     infoAccount.get().getId());

            List<Map<String, String>> dataList = new ArrayList<>();
            BigDecimal totalAll = BigDecimal.ZERO;

            // Iterasi hasil query menggunakan for loop
            for(Object[] row : result) {
                String initialName = (String) row[0];

                // Pastikan konversi tipe data dari Double ke BigDecimal
                BigDecimal totalAmount = row[1] instanceof BigDecimal
                        ?(BigDecimal) row[1] : BigDecimal.valueOf((Double) row[1]);
                String totalAmountToString = totalAmount.setScale(0, RoundingMode.DOWN).toString();

                // Tambahkan totalAmount ke totalAll
                totalAll = totalAll.add(totalAmount);

                // Konversi java.sql.Date ke String
                Date sqlDate = (Date) row[2];
                String initialDate = new SimpleDateFormat("dd-MM-yyyy").format(sqlDate);

                // Tambahkan data ke dataList
                Map<String, String> dataMap = new HashMap<>();
                dataMap.put("categoryName", initialName);
                dataMap.put("total", totalAmountToString);
                dataList.add(dataMap);

            }

            // Setelah iterasi, format totalAll menjadi String
            String totalAllString = totalAll.setScale(0, RoundingMode.DOWN).toString();

            // Buat response akhir dalam bentuk Map
            Map<String, Object> finalResponse = new HashMap<>();
            finalResponse.put("financial_report", dataList); // Tambahkan list response ke dalam key "financial_report"
            finalResponse.put("total_all", totalAllString); // Tambahkan total_all

            return ResponseHandler.generateResponseSuccess(finalResponse);

        } catch (Exception e) {
            return ResponseHandler.generateResponseError(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),"");
        }
    }

}

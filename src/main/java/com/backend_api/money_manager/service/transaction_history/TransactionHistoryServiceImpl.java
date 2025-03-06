package com.backend_api.money_manager.service.transaction_history;

import com.backend_api.money_manager.dto.request.transaction.PageTransactionReq;
import com.backend_api.money_manager.dto.request.transaction.ReportRequest;
import com.backend_api.money_manager.dto.request.transaction.TotalIncomeOrExpenseRequest;
import com.backend_api.money_manager.dto.request.transaction.TransactionRequest;
import com.backend_api.money_manager.dto.response.chart.StickChart;
import com.backend_api.money_manager.dto.response.transaction.IncomeOrExpenseResponse;
import com.backend_api.money_manager.dto.response.transaction.ListTransactionResponse;
import com.backend_api.money_manager.dto.response.transaction.TransactionSummary;
import com.backend_api.money_manager.entity.BalanceUser;
import com.backend_api.money_manager.entity.HistoryTransaction;
import com.backend_api.money_manager.exception.ResponseHandler;
import com.backend_api.money_manager.exception.ResponseHandlerWithPagination;
import com.backend_api.money_manager.helper.InfoAccount;
import com.backend_api.money_manager.helper.StorageUtil;
import com.backend_api.money_manager.repository.BalanceUserRepository;
import com.backend_api.money_manager.repository.CategoryActionRepository;
import com.backend_api.money_manager.repository.CategoryRepository;
import com.backend_api.money_manager.repository.HistoryTransactionRepository;
import jakarta.transaction.Transactional;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class TransactionHistoryServiceImpl implements TransactionHistoryService{

    @Autowired
    CategoryActionRepository categoryActionRepository;

    @Autowired
    BalanceUserRepository balanceUserRepository;

    @Autowired
    InfoAccount infoAccount;

    @Autowired
    TransactionUtil transactionUtil;

    @Autowired
    HistoryTransactionRepository historyTransactionRepository;

    @Override
    public ResponseEntity<Object> createTransaction(TransactionRequest request) {

        try{
            var categoryAction = categoryActionRepository.findById(request.getCategoryAction()).orElseThrow(); //menemukan data dari tabel category action

            BalanceUser balance = balanceUserRepository.findByUserId(infoAccount.get().getId()); //didapat saat melakukan verification, cuman akun yg terverifikasi yg bisa melakukan transaksi

            if(balance == null){
                return ResponseHandler.generateResponseError(HttpStatus.BAD_REQUEST,"","Info Balance Not Found");
            }

            if(categoryAction.getName().equals("Expense")) {

                HistoryTransaction transaction = transactionUtil.createExpand(request);
                return ResponseHandler.generateResponseSuccess(transaction);

            } else if(categoryAction.getName().equals("Income")){

                HistoryTransaction transaction = transactionUtil.createIncome(request);

                return ResponseHandler.generateResponseSuccess(transaction);

            }

            return ResponseHandler.generateResponseError(HttpStatus.BAD_REQUEST,"","Action Failed");

        }catch (Exception e){
            return ResponseHandler.generateResponseError(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),"");

        }

    }

    @Override
    public ResponseEntity<Object> getListTransaction(PageTransactionReq request) {

        List<ListTransactionResponse> data = transactionUtil.getListUserTransaction(request);
        int totalPage =  historyTransactionRepository.countAllDataByUser(infoAccount.get().getId(),request.getStartDate(), request.getEndDate());

        ResponseHandlerWithPagination.InfoPage pages = new ResponseHandlerWithPagination.InfoPage();
        pages.setTotalPage(totalPage);
        pages.setOffset(request.getOffset());
        pages.setLimit(request.getLimit());
        pages.setCurrentPage(request.getOffset());

        ResponseHandlerWithPagination.Data response = new ResponseHandlerWithPagination.Data();
        response.setPage(pages);
        response.setData(data);

        return ResponseHandlerWithPagination.generateResponseSuccess(response);
    }

    @Override
    public ResponseEntity<Object> getId(String id) {
        return null;
    }


    @Override
    public ResponseEntity<Object> reportTransactionUser(ReportRequest request) {
        try{
            List<Object[]> result = historyTransactionRepository.transactionSummaryByDate(request.getStartDate(), request.getEndDate(), infoAccount.get().getId(), request.getActionId());

            List<TransactionSummary> response = result.stream()
                    .map(row -> {

                        String initialDate = (String) row[0];
                        BigDecimal totalAmount = (BigDecimal) row[1];
                        String total = totalAmount.setScale(0, RoundingMode.DOWN).toString();

                        return new TransactionSummary(initialDate, total);
                    })
                    .toList();

            return ResponseHandler.generateResponseSuccess(response);
        }catch (Exception e){
            return ResponseHandler.generateResponseError(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),"");
        }
    }

    @Override
    public ResponseEntity<Object> userExpenseOrIncomeChartStick(ReportRequest request) {
        try{
            String startDate = request.getStartDate();
            String endDate = request.getEndDate();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            if (startDate == null || startDate.isEmpty()) {
                LocalDate today = LocalDate.now();
                LocalDate sevenDaysAgo = today.minusDays(7);
                startDate = sevenDaysAgo.format(formatter);
            }

            if (endDate == null || endDate.isEmpty()) {
                endDate = LocalDate.now().format(formatter);
            }



            List<Object[]> query = historyTransactionRepository.chartStickDayBetween(startDate, endDate, request.getActionId(), infoAccount.get().getId());
            List<StickChart> response = query.stream()
                    .map(row -> {

                        String initialDate = (String) row[0];
                        Object totalAmountObject = row[1];

                        BigDecimal totalAmount = BigDecimal.ZERO;

                        if (totalAmountObject instanceof Double) {
                            totalAmount = new BigDecimal((Double) totalAmountObject);
                        } else if (totalAmountObject instanceof BigDecimal) {
                            totalAmount = (BigDecimal) totalAmountObject;
                        }

                        String total = totalAmount.setScale(0, RoundingMode.DOWN).toString();
                        return new StickChart(initialDate, total);

                    })
                    .toList();

            return ResponseHandler.generateResponseSuccess(response);
        }catch (Exception e){
            return ResponseHandler.generateResponseError(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),"");
        }

    }

    @Override
    public ResponseEntity<Object> totalIncomeOrExpense(TotalIncomeOrExpenseRequest request) {
        try{
            IncomeOrExpenseResponse res = transactionUtil.infoIncomeToday(request);
            return ResponseHandler.generateResponseSuccess(res);
        }catch (Exception e){
            return ResponseHandler.generateResponseError(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),"");

        }
    }

}

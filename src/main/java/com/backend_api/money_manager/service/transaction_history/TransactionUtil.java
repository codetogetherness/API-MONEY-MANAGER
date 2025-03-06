package com.backend_api.money_manager.service.transaction_history;

import com.backend_api.money_manager.dto.request.transaction.PageTransactionReq;
import com.backend_api.money_manager.dto.request.transaction.TotalIncomeOrExpenseRequest;
import com.backend_api.money_manager.dto.request.transaction.TransactionRequest;
import com.backend_api.money_manager.dto.response.transaction.IncomeOrExpenseResponse;
import com.backend_api.money_manager.dto.response.transaction.ListTransactionResponse;
import com.backend_api.money_manager.dto.response.transaction.TransactionSummary;
import com.backend_api.money_manager.entity.*;
import com.backend_api.money_manager.exception.ResponseHandler;
import com.backend_api.money_manager.helper.InfoAccount;
import com.backend_api.money_manager.helper.MailService;
import com.backend_api.money_manager.helper.StorageUtil;
import com.backend_api.money_manager.repository.*;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionUtil {

    @Autowired
    StorageUtil storageUtil;
    @Autowired
    BalanceUserRepository balanceUserRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    NotificationReadRepository notificationReadRepository;

    @Autowired
    InfoAccount infoAccount;

    @Autowired
    CategoryActionRepository categoryActionRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    HistoryTransactionRepository historyTransactionRepository;

    @Autowired
    BudgetRepository budgetRepository;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    MailService mailService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    private static final String UPLOAD_FOLDER_NAME = "history_transaction";

    @SneakyThrows
    public HistoryTransaction createExpand(TransactionRequest request){

        String user = infoAccount.get().getEmail();

        var categoryAction = categoryActionRepository.findById(request.getCategoryAction()).orElseThrow();
        var category = categoryRepository.findById(request.getCategoryId()).orElseThrow();
        List<Object[]> totalExpand = historyTransactionRepository.totalExpandUserByCategory(infoAccount.get().getId(), request.getCategoryAction(), request.getCategoryId());

        List<Object[]> dataBudget = budgetRepository.findAmountUserByCategory(infoAccount.get().getId(), request.getCategoryId());

        BalanceUser balance = balanceUserRepository.findByUserId(infoAccount.get().getId());

        var find = usersRepository.findByEmail(user).orElseThrow();

        HistoryTransaction historyTransaction = new HistoryTransaction();
        historyTransaction.setId(UUID.randomUUID().toString());
        historyTransaction.setAmount(request.getAmount());
        historyTransaction.setCategory(category);
        historyTransaction.setCategoryAction(categoryAction);
        historyTransaction.setUser(infoAccount.get());

        if (request.getBase64() != null && !request.getBase64().isEmpty()) {
            File file = storageUtil.convertBase64ToFile(request.getBase64(), request.getFileName());
            if (file != null) {

                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                Path targetPath = uploadPath.resolve(file.getName());
                Files.copy(file.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
                historyTransaction.setImage("/uploads/" + file.getName());
                Files.deleteIfExists(file.toPath());
            }
        } else {
            historyTransaction.setImage(null);
        }

        historyTransaction.setDescription(request.getDescription());
        historyTransaction.setTitle(request.getTitle());
        historyTransactionRepository.save(historyTransaction);

        Notification notification = new Notification();
        notification.setId(UUID.randomUUID().toString());
        notification.setTitle(request.getTitle());
        notification.setDetail(request.getDescription());
        notification.setUser(find);
        var dataNotification = notificationRepository.save(notification);

        NotificationRead notificationRead = new NotificationRead();
        notificationRead.setId(UUID.randomUUID().toString());
        notificationRead.setUser(find);
        notificationRead.setNotification(dataNotification);
        notificationRead.setIsRead(false);
        notificationReadRepository.save(notificationRead);

        Object[] result = !totalExpand.isEmpty() ? totalExpand.get(0) : null;

        int totalExpWithAmount = 0;
        if (result != null ) {
            totalExpWithAmount = ((Number) result[0]).intValue();
        }

        totalExpWithAmount += Integer.parseInt(request.getAmount());
        
        int totalBudget = 0;
        if(!dataBudget.isEmpty()){
          totalBudget = Integer.parseInt(dataBudget.get(0)[0].toString());
        }

        if(!dataBudget.isEmpty()){
            if(totalExpWithAmount > totalBudget) {
                Users data = usersRepository.findByEmail(user).orElseThrow();
                Context context = new Context();
                context.setVariable("name", data.getFullName());
                context.setVariable("orderNumber", totalExpWithAmount);
                context.setVariable("budgetAmount", totalBudget);

                String emailContent = templateEngine.process("budget_alert", context);
                mailService.sendEmail(data.getEmail(), "Budget Exceeded Alert", emailContent);
            }
        }

        int total = Integer.parseInt(balance.getValue()) - Integer.parseInt(request.getAmount());
        balance.setValue(String.valueOf(total));
        balanceUserRepository.save(balance);

        return historyTransaction;
    }


    @SneakyThrows
    public HistoryTransaction createIncome(TransactionRequest request){

        String user = infoAccount.get().getEmail();

        var categoryAction = categoryActionRepository.findById(request.getCategoryAction()).orElseThrow();
        var category = categoryRepository.findById(request.getCategoryId()).orElseThrow();

        BalanceUser balance = balanceUserRepository.findByUserId(infoAccount.get().getId());

        var find = usersRepository.findByEmail(user).orElseThrow();

        HistoryTransaction historyTransaction = new HistoryTransaction();
        historyTransaction.setId(UUID.randomUUID().toString());
        historyTransaction.setAmount(request.getAmount());
        historyTransaction.setCategory(category);
        historyTransaction.setCategoryAction(categoryAction);
        historyTransaction.setUser(infoAccount.get());

        if (request.getBase64() != null && !request.getBase64().isEmpty()) {
            File file = storageUtil.convertBase64ToFile(request.getBase64(), request.getFileName());
            if (file != null) {

                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                Path targetPath = uploadPath.resolve(file.getName());
                Files.copy(file.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
                historyTransaction.setImage("/uploads/" + file.getName());
                Files.deleteIfExists(file.toPath());
            }
        } else {
            historyTransaction.setImage(null);
        }

        historyTransaction.setDescription(request.getDescription());
        historyTransaction.setTitle(request.getTitle());
        historyTransactionRepository.save(historyTransaction);

        Notification notification = new Notification();
        notification.setId(UUID.randomUUID().toString());
        notification.setTitle(request.getTitle());
        notification.setDetail(request.getDescription());
        notification.setUser(find);
        var dataNotification = notificationRepository.save(notification);

        NotificationRead notificationRead = new NotificationRead();
        notificationRead.setId(UUID.randomUUID().toString());
        notificationRead.setUser(find);
        notificationRead.setNotification(dataNotification);
        notificationRead.setIsRead(false);
        notificationReadRepository.save(notificationRead);

        int total = Integer.parseInt(balance.getValue()) + Integer.parseInt(request.getAmount());
        balance.setValue(String.valueOf(total));

        balanceUserRepository.save(balance);

        return historyTransaction;
    }


    public List<ListTransactionResponse> getListUserTransaction(PageTransactionReq request){

        Pageable page = PageRequest.of(request.getOffset(), request.getLimit(), Sort.by(Sort.Order.desc("createdAt")));

        List<HistoryTransaction> res = historyTransactionRepository.findAllDataByUser(
                infoAccount.get().getId(),
                request.getStartDate(),
                request.getEndDate(),
                page
        );

        List<ListTransactionResponse> transactions = new ArrayList<>();

        for (HistoryTransaction re : res) {
            ListTransactionResponse obj = new ListTransactionResponse();

            obj.setId(re.getId());

            ListTransactionResponse.User user = new ListTransactionResponse.User();
            user.setFullName(re.getUser().getFullName());
            user.setEmail(re.getUser().getEmail());
            user.setId(re.getUser().getId());
            obj.setUser(user);

            ListTransactionResponse.Category category = new ListTransactionResponse.Category();

            category.setIcon(re.getCategory().getIcon());
            category.setName(re.getCategory().getName());
            category.setId(re.getCategory().getId());
            obj.setCategory(category);

            obj.setTotal(re.getAmount());
            obj.setCreatedAt(re.getCreatedAt().toString());
            obj.setDescription(re.getDescription());
            obj.setTitle(re.getTitle());
            obj.setTypeAction(re.getCategoryAction().getName());

            transactions.add(obj);

        }

        return transactions;
    }

    public IncomeOrExpenseResponse infoIncomeToday(TotalIncomeOrExpenseRequest request){
        IncomeOrExpenseResponse data = new IncomeOrExpenseResponse();

        List<Object[]> total = historyTransactionRepository.findAllIncomeOrExpenseToday(infoAccount.get().getId(),request.getActionId(), request.getStartDate(), request.getEndDate());

        if (!total.isEmpty() && total.get(0) != null) {
            Object firstElement = total.get(0)[0];
            Object secondElement = total.get(0)[1];

            if (firstElement != null) {
                data.setTotal(String.valueOf(firstElement));
            } else {
                data.setTotal("0");
            }

            if (secondElement != null) {
                data.setName((String) secondElement);
            } else {
                data.setName(request.getActionId());
            }
        } else {
            data.setName(request.getActionId());
            data.setTotal("unknown error");
        }

        return data;
    }

}

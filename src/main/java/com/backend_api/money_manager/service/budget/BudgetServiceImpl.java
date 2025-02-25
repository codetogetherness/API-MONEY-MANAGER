package com.backend_api.money_manager.service.budget;

import com.backend_api.money_manager.dto.request.budget.BudgetIdRequest;
import com.backend_api.money_manager.dto.request.budget.BudgetRequest;
import com.backend_api.money_manager.dto.response.budget.BudgetResponse;
import com.backend_api.money_manager.entity.Budget;
import com.backend_api.money_manager.exception.ResponseHandler;
import com.backend_api.money_manager.helper.InfoAccount;
import com.backend_api.money_manager.repository.BudgetRepository;
import com.backend_api.money_manager.repository.CategoryRepository;
import com.backend_api.money_manager.repository.UsersRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class BudgetServiceImpl implements BudgetService{

    @Autowired
    InfoAccount infoAccount;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    BudgetRepository budgetRepository;

    @Override
    public ResponseEntity<Object> create(BudgetRequest request) {

        String user = infoAccount.get().getEmail();

        if(user == null){
            return ResponseHandler.generateResponseError(HttpStatus.BAD_REQUEST,"FAILED","Email belum terdaftar");
        }

        var find = usersRepository.findByEmail(user).orElseThrow();
        var category = categoryRepository.findById(request.getCategoryId()).orElseThrow();
        int currentMonth = LocalDateTime.now().getMonthValue();
        String currentMonthString = String.format("%02d", currentMonth);
        System.out.println(currentMonthString);

        var existingBudget = budgetRepository.findUserCreateCategoryByMonth(request.getCategoryId(), infoAccount.get().getId(), currentMonthString);

        if (existingBudget != null && !existingBudget.isEmpty()) {
            return ResponseHandler.generateResponseError(HttpStatus.BAD_REQUEST,"FAILED","Category untuk bulan ini sudah ada");
        }

        ModelMapper modelMapper = new ModelMapper();
        Budget budgetMapper = modelMapper.map(request, Budget.class);
        budgetMapper.setId(UUID.randomUUID().toString());
        budgetMapper.setAmount(request.getAmount());
        budgetMapper.setCategory(category);
        budgetMapper.setUser(infoAccount.get());

        var data = budgetRepository.save(budgetMapper);
        return ResponseHandler.generateResponseSuccess(data.getCategory().getName());
    }

    @Override
    public ResponseEntity<Object> update(BudgetIdRequest request) {
        String user = infoAccount.get().getEmail();

        if(user == null){
            return ResponseHandler.generateResponseError(HttpStatus.BAD_REQUEST,"FAILED","Email belum terdaftar");
        }
        var budget = budgetRepository.findById(request.getId()).orElseThrow();
        var category = categoryRepository.findById(request.getCategoryId()).orElseThrow();
        budget.setAmount(request.getAmount());
        budget.setCategory(category);

        var data = budgetRepository.save(budget);
        return ResponseHandler.generateResponseSuccess(data);
    }

    @Override
    public ResponseEntity<Object> getAll(BudgetResponse response) {
        var data = budgetRepository.findAll();
        return ResponseHandler.generateResponseSuccess(data);
    }

    @Override
    public ResponseEntity<Object> getId(String id) {
        var data = budgetRepository.findById(id).orElseThrow();
        return ResponseHandler.generateResponseSuccess(data);
    }

    @Override
    public ResponseEntity<Object> delete(BudgetIdRequest request) {
        var budget = budgetRepository.findById(request.getId()).orElseThrow();
        budget.setIsDelete(true);

        var data = budgetRepository.save(budget);
        return ResponseHandler.generateResponseSuccess(data);
    }
}

package com.backend_api.money_manager.repository;

import com.backend_api.money_manager.entity.CategoryTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryTransactionRepository extends JpaRepository<CategoryTransaction,String> {
}

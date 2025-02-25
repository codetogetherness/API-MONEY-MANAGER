package com.backend_api.money_manager.repository;

import com.backend_api.money_manager.entity.PaymentCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentCategoryRepository extends JpaRepository<PaymentCategory,String> {
}

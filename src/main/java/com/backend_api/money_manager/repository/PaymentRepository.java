package com.backend_api.money_manager.repository;

import com.backend_api.money_manager.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface PaymentRepository extends JpaRepository<Payment,String> {
    Payment findByCode(@Param("code") String code);
}

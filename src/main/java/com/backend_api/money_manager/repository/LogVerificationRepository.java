package com.backend_api.money_manager.repository;

import com.backend_api.money_manager.entity.LogVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface LogVerificationRepository extends JpaRepository<LogVerification,String> {
    LogVerification findByUserEmailAndExpired(@Param("email") String email,@Param("date") String date);
}

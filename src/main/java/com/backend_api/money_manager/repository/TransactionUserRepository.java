package com.backend_api.money_manager.repository;

import com.backend_api.money_manager.entity.TransactionUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TransactionUserRepository extends JpaRepository<TransactionUser,String> {
    TransactionUser findByCode(@Param("code") String code);

    @Query(value = "SELECT * FROM transaction_user t WHERE t.user_id = :userId AND DATE(t.created_at) = :createdAt AND t.is_active = false ORDER BY t.created_at DESC LIMIT 1", nativeQuery = true)
    TransactionUser findTransactionNow(@Param("userId") String userId,@Param("createdAt") String createdAt);

    @Query(value = "SELECT * FROM transaction_user t WHERE t.user_id = :userId AND t.is_active = true ORDER BY t.created_at DESC LIMIT 1", nativeQuery = true)
    Optional<TransactionUser> findLatestTransactionUserByUserId(@Param("userId") String userId);

}

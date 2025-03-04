package com.backend_api.money_manager.repository;

import com.backend_api.money_manager.entity.BalanceUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceUserRepository extends JpaRepository<BalanceUser,String> {
    BalanceUser findByUserId(@Param("userid") String userId);
}

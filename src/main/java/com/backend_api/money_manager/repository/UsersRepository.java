package com.backend_api.money_manager.repository;

import com.backend_api.money_manager.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users,String> {
    Optional<Users> findByEmail(@Param("email") String email);
}

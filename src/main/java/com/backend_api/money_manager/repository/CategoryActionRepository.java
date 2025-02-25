package com.backend_api.money_manager.repository;

import com.backend_api.money_manager.entity.CategoryAction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryActionRepository extends JpaRepository<CategoryAction,String> {
}

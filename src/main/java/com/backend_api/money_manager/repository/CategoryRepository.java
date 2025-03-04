package com.backend_api.money_manager.repository;

import com.backend_api.money_manager.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,String> {

    @Query("SELECT c FROM Category c WHERE c.categoryAction.name = :name")
    List<Category> findAllByCategoryActions(String name);
}

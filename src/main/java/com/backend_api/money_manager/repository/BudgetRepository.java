package com.backend_api.money_manager.repository;

import com.backend_api.money_manager.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget, String> {
    Budget findByUserId(@Param("userid") String userid);

    @Query(value = "SELECT amount FROM budget b WHERE b.user_id = :userId AND b.category_id = :categoryId", nativeQuery = true)
    List<Object[]> findAmountUserByCategory(@Param("userId") String userId, @Param("categoryId") String categoryId);

    @Query(value = "SELECT EXISTS(\n" +
            "    SELECT 1 \n" +
            "    FROM budget b \n" +
            "    WHERE b.user_id = :userId AND b.category_id = :categoryId\n" +
            ") AS result \n", nativeQuery = true)
    List<Object[]> findAmountUserByCategoryCheck(@Param("userId") String userId, @Param("categoryId") String categoryId);

    @Query(value = "SELECT " +
            "category_id, " +
            "user_id, " +
            "created_at " +
            "FROM " +
            "budget b " +
            "WHERE " +
            "b.category_id = :categoryId " +
            "AND b.user_id = :userId " +
            "AND MONTH(b.created_at) = :month", nativeQuery = true)
    List<Object[]> findUserCreateCategoryByMonth(@Param("categoryId") String categoryId, @Param("userId") String userId, @Param("month") String month);

    @Query("SELECT b FROM Budget b WHERE b.category.id = :categoryId AND b.user.id = :userId AND FUNCTION('MONTH', b.createdAt) = :month ")
    List<Object[]> findUserCreateCategoryByMonthNotNative(@Param("categoryId") String categoryId, @Param("userId") String userId, @Param("month") String month);
}

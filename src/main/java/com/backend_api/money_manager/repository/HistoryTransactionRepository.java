package com.backend_api.money_manager.repository;

import com.backend_api.money_manager.entity.HistoryTransaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HistoryTransactionRepository extends JpaRepository<HistoryTransaction,String> {

    @Query("SELECT h FROM HistoryTransaction h WHERE h.user.id = :userId AND FUNCTION('TO_CHAR', h.createdAt, 'DDMMYYYY') BETWEEN :startDate AND :endDate")
    List<HistoryTransaction> findAllDataByUser(@Param("userId") String userId, @Param("startDate") String startDate,@Param("endDate") String endDate, Pageable pageable);

    @Query("SELECT COUNT(h) FROM HistoryTransaction h WHERE h.user.id = :userId AND FUNCTION('TO_CHAR', h.createdAt, 'DDMMYYYY') BETWEEN :startDate AND :endDate")
    int countAllDataByUser(@Param("userId") String userId, @Param("startDate") String startDate, @Param("endDate") String endDate);

    @Query(value = "SELECT " +
            "SUM(ht.amount) AS total_amount, " +
            "    CASE " +
            "        WHEN ca.name = 'Expense' THEN 'Expense' " +
            "        WHEN ca.name = 'Income' THEN 'Income' " +
            "        ELSE ca.name " +
            "    END AS category_name " +
            "FROM " +
            "    history_transaction ht " +
            "INNER JOIN " +
            "    category_action ca ON ht.category_action_id = ca.id " +
            "WHERE " +
            "    DATE(ht.created_at) BETWEEN STR_TO_DATE(:startDate, '%d-%m-%Y') " +
            "    AND STR_TO_DATE(:endDate, '%d-%m-%Y') " +
            "    AND ht.user_id = :userId " +
            "    AND ca.name = :actionId " +
            "GROUP BY " +
            "    category_name;",
            nativeQuery = true)
    List<Object[]> findAllIncomeOrExpenseToday(@Param("userId") String userId, @Param("actionId") String actionId,
                                       @Param("startDate") String startDate, @Param("endDate") String endDate);

    @Query(value = "SELECT " +
            "SUM(ht.amount) AS total_expense " +
            "FROM " +
            "history_transaction ht " +
            "INNER JOIN category_action ca ON " +
            "ca.id = ht.category_action_id " +
            "WHERE " +
            "ca.name = :actionId " +
            "AND ht.user_id = :userId " +
            "AND ht.category_id = :categoryId",
            nativeQuery = true)
    List<Object[]> totalExpandUserByCategory(@Param("userId") String userId, @Param("actionId") String actionId,
                                             @Param("categoryId") String categoryId);

    @Query(value = "SELECT " +
            "c.name, " +
            "SUM(ht.amount) AS totalAmount, " +
            "DATE(ht.created_at) AS createdAt " +
            "FROM " +
            "history_transaction ht " +
            "JOIN category c ON " +
            "c.id = ht.category_id " +
            "WHERE " +
            "ht.category_action_id = :categoryAction " +
            "AND DATE(ht.created_at) BETWEEN :startDate " +
            "AND :endDate " +
            "AND ht.user_id = :userId " +
            "GROUP BY " +
            "c.name",
            nativeQuery = true)

    List<Object[]> totalAmountUserByMonth(@Param("categoryAction") String categoryAction,
                                          @Param("startDate") String startDate, @Param("endDate") String endDate,
                                          @Param("userId") String userId);






    @Query(value = "WITH RECURSIVE date_range AS ( " +
            "    SELECT DATE(:start) as initial_date " +
            "    UNION ALL " +
            "    SELECT DATE_ADD(initial_date, INTERVAL 1 DAY) " +
            "    FROM date_range " +
            "    WHERE initial_date < :end " +
            ") " +
            "SELECT " +
            "    DATE_FORMAT(dr.initial_date, '%d-%m-%Y') AS initial_date, " +
            "    COALESCE(SUM(CAST(ht.amount AS INT)), 0) AS total " +
            "FROM date_range dr " +
            "LEFT JOIN history_transaction ht " +
            "    ON DATE(ht.created_at) = dr.initial_date " +
            "    AND ht.created_at BETWEEN :start AND :end " +
            "    AND ht.user_id = :userId  " +
            "    AND ht.category_action_id = :categoryActionId " +
            "GROUP BY dr.initial_date " +
            "ORDER BY dr.initial_date",nativeQuery = true)
    List<Object[]> transactionSummaryByDate(@Param("start") String start, @Param("end") String end,
                                                 @Param("categoryActionId")String categoryActionId,
                                                 @Param("userId")String userId);

   @Query(value = "SELECT\n" +
           "DATE_FORMAT(DATE(STR_TO_DATE(:startDate, '%d-%m-%Y')) + INTERVAL a DAY, '%d-%m-%Y') AS label,\n" +
           "(\n" +
           "SELECT\n" +
           "IFNULL(SUM(ht.amount), 0) \n" +
           "FROM\n" +
           "history_transaction ht\n" +
           "WHERE\n" +
           "ht.user_id = :userId\n" +
           "AND ht.category_action_id = :categoryAction\n" +
           "AND DATE_FORMAT(ht.created_at, '%d-%m-%Y') = DATE_FORMAT(DATE(STR_TO_DATE(:startDate, '%d-%m-%Y')) + INTERVAL a DAY, '%d-%m-%Y')  -- Bandingkan dengan tanggal yang dihasilkan\n" +
           ") AS total\n" +
           "FROM\n" +
           "(\n" +
           "SELECT @rownum := @rownum + 1 AS a\n" +
           "FROM information_schema.columns, (SELECT @rownum := -1) r\n" +
           "LIMIT 100\n" +
           ") d\n" +
           "WHERE\n" +
           "DATE(STR_TO_DATE(:startDate, '%d-%m-%Y')) + INTERVAL a DAY <= STR_TO_DATE(:endDate, '%d-%m-%Y')\n" +
           "ORDER BY label ",nativeQuery = true)
    List<Object[]> chartStickDayBetween(String startDate,String endDate,String categoryAction,String userId);

    @Query(value = "WITH RECURSIVE date_range AS ( " +
            "    SELECT DATE(:start) as initial_date " +
            "    UNION ALL " +
            "    SELECT DATE_ADD(initial_date, INTERVAL 1 DAY) " +
            "    FROM date_range " +
            "    WHERE initial_date < :end " +
            ") " +
            "SELECT " +
            "    DATE_FORMAT(dr.initial_date, '%d-%m-%Y') AS initial_date, " +
            "    COALESCE(SUM(CAST(ht.amount AS INT)), 0) AS total " +
            "FROM date_range dr " +
            "LEFT JOIN history_transaction ht " +
            "    ON DATE(ht.created_at) = dr.initial_date " +
            "    AND ht.created_at BETWEEN :start AND :end " +
            "    AND ht.user_id = :userId  " +
            "    AND ht.category_action_id = :categoryActionId " +
            "GROUP BY dr.initial_date " +
            "ORDER BY dr.initial_date",nativeQuery = true)
    List<Object[]> transactionSummaryByDate(@Param("start") String start, @Param("end") String end,
                                                 @Param("categoryActionId")String categoryActionId,
                                                 @Param("userId")String userId);

   @Query(value = "SELECT\n" +
           "DATE_FORMAT(DATE(STR_TO_DATE(:startDate, '%d-%m-%Y')) + INTERVAL a DAY, '%d-%m-%Y') AS label,\n" +
           "(\n" +
           "SELECT\n" +
           "IFNULL(SUM(ht.amount), 0) \n" +
           "FROM\n" +
           "history_transaction ht\n" +
           "WHERE\n" +
           "ht.user_id = :userId\n" +
           "AND ht.category_action_id = :categoryAction\n" +
           "AND DATE_FORMAT(ht.created_at, '%d-%m-%Y') = DATE_FORMAT(DATE(STR_TO_DATE(:startDate, '%d-%m-%Y')) + INTERVAL a DAY, '%d-%m-%Y')  -- Bandingkan dengan tanggal yang dihasilkan\n" +
           ") AS total\n" +
           "FROM\n" +
           "(\n" +
           "SELECT @rownum := @rownum + 1 AS a\n" +
           "FROM information_schema.columns, (SELECT @rownum := -1) r\n" +
           "LIMIT 100\n" +
           ") d\n" +
           "WHERE\n" +
           "DATE(STR_TO_DATE(:startDate, '%d-%m-%Y')) + INTERVAL a DAY <= STR_TO_DATE(:endDate, '%d-%m-%Y')\n" +
           "ORDER BY label ",nativeQuery = true)
    List<Object[]> chartStickDayBetween(String startDate,String endDate,String categoryAction,String userId);

}

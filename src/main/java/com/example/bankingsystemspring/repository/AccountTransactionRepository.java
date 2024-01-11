package com.example.bankingsystemspring.repository;

import com.example.bankingsystemspring.model.AccountTransactionsEntity;
import com.example.bankingsystemspring.model.UserAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface AccountTransactionRepository extends JpaRepository<AccountTransactionsEntity, UUID> {
  List<AccountTransactionsEntity> findByAccount(UserAccountEntity account);
  @Query("SELECT t FROM AccountTransactionsEntity t WHERE t.account.accountId = :accountId " +
          "AND t.transactionDate between :startDate and :endDate")
  List<AccountTransactionsEntity> findByAccountAndPeriod(
          @Param("accountId") UUID accountId,
          @Param("startDate") Date startDate,
          @Param("endDate") Date endDate);
}
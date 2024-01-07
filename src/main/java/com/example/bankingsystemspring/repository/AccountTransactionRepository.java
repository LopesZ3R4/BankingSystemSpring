package com.example.bankingsystemspring.repository;

import com.example.bankingsystemspring.model.AccountTransactionsEntity;
import com.example.bankingsystemspring.model.UserAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AccountTransactionRepository extends JpaRepository<AccountTransactionsEntity, UUID> {
  List<AccountTransactionsEntity> findByAccount(UserAccountEntity account);
  List<AccountTransactionsEntity> findByDestinationAccount(UserAccountEntity destinationAccount);
}
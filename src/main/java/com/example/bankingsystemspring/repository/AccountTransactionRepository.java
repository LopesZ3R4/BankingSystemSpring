package com.example.bankingsystemspring.repository;

import com.example.bankingsystemspring.model.AccountTransactionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountTransactionRepository extends JpaRepository<AccountTransactionsEntity, UUID> {
}
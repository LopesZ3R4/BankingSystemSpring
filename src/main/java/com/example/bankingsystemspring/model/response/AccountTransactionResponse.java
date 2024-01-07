package com.example.bankingsystemspring.model.response;

import com.example.bankingsystemspring.common.enums.TransactionType;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

public class AccountTransactionResponse {
    final private UUID transactionId;
    final private Timestamp transactionDate;
    final private TransactionType transactionType;
    final private BigDecimal amount;
    final private BigDecimal balance;

    public AccountTransactionResponse(UUID transactionId, Timestamp transactionDate, TransactionType transactionType, BigDecimal amount, BigDecimal balance) {
        this.transactionId = transactionId;
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
        this.amount = amount;
        this.balance = balance;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public Timestamp getTransactionDate() {
        return transactionDate;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
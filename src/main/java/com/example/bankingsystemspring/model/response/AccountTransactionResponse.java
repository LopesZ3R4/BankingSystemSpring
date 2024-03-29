package com.example.bankingsystemspring.model.response;

import com.example.bankingsystemspring.common.enums.TransactionType;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

public class AccountTransactionResponse {
    private UUID transactionId;
    private Timestamp transactionDate;
    private TransactionType transactionType;
    private String transactionDescription;
    private BigDecimal amount;
    private BigDecimal balance;
    public AccountTransactionResponse(){}

    public AccountTransactionResponse(UUID transactionId, Timestamp transactionDate, TransactionType transactionType, String transactionDescription, BigDecimal amount, BigDecimal balance) {
        this.transactionId = transactionId;
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
        this.transactionDescription = transactionDescription;
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
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getTransactionDescription() {
        return transactionDescription;
    }

    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }
}
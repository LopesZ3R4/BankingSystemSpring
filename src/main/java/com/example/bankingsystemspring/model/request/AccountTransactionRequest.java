package com.example.bankingsystemspring.model.request;

import com.example.bankingsystemspring.common.TransactionType;
import java.math.BigDecimal;
import java.util.UUID;

public class AccountTransactionRequest {
    private UUID accountId;
    private TransactionType transactionType;
    private BigDecimal amount;
    private UUID destinationAccountId;

    public AccountTransactionRequest(UUID accountId, TransactionType transactionType, BigDecimal amount, UUID destinationAccountId) {
        this.accountId = accountId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.destinationAccountId = destinationAccountId;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public UUID getDestinationAccountId() {
        return destinationAccountId;
    }

    public void setDestinationAccountId(UUID destinationAccountId) {
        this.destinationAccountId = destinationAccountId;
    }
}
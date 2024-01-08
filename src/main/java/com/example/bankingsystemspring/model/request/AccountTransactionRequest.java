package com.example.bankingsystemspring.model.request;

import java.math.BigDecimal;
import java.util.UUID;

public class AccountTransactionRequest {
    private UUID accountId;
    private BigDecimal amount;
    private String chavePix;
    public AccountTransactionRequest(){}

    public AccountTransactionRequest(UUID accountId, BigDecimal amount, String chavePix) {
        this.accountId = accountId;
        this.amount = amount;
        this.chavePix = chavePix;
    }
    public AccountTransactionRequest(UUID accountId, BigDecimal amount) {
        this.accountId = accountId;
        this.amount = amount;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getChavePix() {
        return chavePix;
    }

    public AccountTransactionRequest setChavePix(String chavePix) {
        this.chavePix = chavePix;
        return this;
    }
}
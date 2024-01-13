package com.example.bankingsystemspring.model.response;

import java.math.BigDecimal;
import java.util.UUID;

public class UserAccountResponse {
    private final UUID accountId;
    private final String chavePix;
    private final String name;
    private BigDecimal balance;

    public UserAccountResponse(UUID accountId, String chavePix, String name, BigDecimal balance) {
        this.accountId = accountId;
        this.chavePix = chavePix;
        this.name = name;
        this.balance = balance;
    }

    public String getChavePix() {
        return chavePix;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public UserAccountResponse setBalance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }
}

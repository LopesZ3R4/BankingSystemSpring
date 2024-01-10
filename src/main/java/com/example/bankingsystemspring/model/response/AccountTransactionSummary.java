package com.example.bankingsystemspring.model.response;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class AccountTransactionSummary {
    private final UUID accountId;
    private final String chavePix;
    private final String name;
    private final BigDecimal balance;
    private final List<AccountTransactionResponse> accountTransactionslist;

    public AccountTransactionSummary(UUID accountId, String chavePix, String name, BigDecimal balance, List<AccountTransactionResponse> accountTransactionslist) {
        this.accountId = accountId;
        this.chavePix = chavePix;
        this.name = name;
        this.balance = balance;
        this.accountTransactionslist = accountTransactionslist;
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

    public List<AccountTransactionResponse> getAccountTransactionslist() {
        return accountTransactionslist;
    }
}

package com.example.bankingsystemspring.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "Accounts")
public class UserAccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AccountID")
    private UUID accountId;
    @Column(name = "ChavePix", nullable = false)
    private String chavePix;

    @Column(name = "AccountNumber", unique = true, nullable = false)
    private String accountNumber;

    @Column(name = "AccountHolderName", nullable = false)
    private String accountHolderName;

    @Column(name = "Balance", nullable = false)
    private BigDecimal balance;

    @Column(name = "CreatedAt", updatable = false)
    private Timestamp createdAt;

    @Column(name = "UpdatedAt")
    private Timestamp updatedAt;

    public UUID getAccountId() {
        return accountId;
    }

    public UserAccountEntity setAccountId(UUID accountId) {
        this.accountId = accountId;
        return this;
    }

    public String getChavePix() {
        return chavePix;
    }

    public UserAccountEntity setChavePix(String chavePix) {
        this.chavePix = chavePix;
        return this;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public UserAccountEntity setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public UserAccountEntity setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
        return this;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public UserAccountEntity setBalance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }
}
package com.example.bankingsystemspring.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "Accounts")
public class UserAccountEntity {

    @Id
    @Column(name = "AccountID")
    private UUID accountId;
    @Column(name = "ChavePix", nullable = false)
    private String chavePix;

    @Column(name = "AccountHolderName", nullable = false)
    private String accountHolderName;

    @Column(name = "Balance", nullable = false)
    private BigDecimal balance;

    @CreationTimestamp
    @Column(name = "CreatedAt", updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "UpdatedAt")
    private Timestamp updatedAt;

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public String getChavePix() {
        return chavePix;
    }

    public UserAccountEntity setChavePix(String chavePix) {
        this.chavePix = chavePix;
        return this;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }
}
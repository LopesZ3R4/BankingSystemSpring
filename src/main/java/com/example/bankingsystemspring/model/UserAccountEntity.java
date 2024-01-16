package com.example.bankingsystemspring.model;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.UUID;

import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "Accounts")
public class UserAccountEntity {

    @Id
    @Column(name = "AccountID")
    private UUID accountId;
    @Column(name = "ChavePix", nullable = false, unique = true)
    private String chavePix;

    @Column(name = "AccountHolderName", nullable = false)
    private String accountHolderName;
    @Column(name = "CPF", nullable = false, length = 12)
    @Size(max = 12)
    private String CPF;

    @CreationTimestamp
    @Column(name = "CreatedAt", updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "UpdatedAt")
    private Timestamp updatedAt;
    public UserAccountEntity(){}
    public UserAccountEntity(String accountHolderName, String CPF, String chavePix){

    }

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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }
}
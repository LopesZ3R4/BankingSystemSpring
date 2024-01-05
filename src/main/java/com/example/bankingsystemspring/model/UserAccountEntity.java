package com.example.bankingsystemspring.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "Account")
public class UserAccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AccountID")
    private Long accountId;

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

    // Getters and Setters
}
package com.example.bankingsystemspring.model;
import com.example.bankingsystemspring.common.enums.TransactionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "Transactions")
public class AccountTransactionsEntity {

    @Id
    @Column(name = "TransactionID")
    private UUID transactionId;

    @ManyToOne
    @JoinColumn(name = "AccountID", nullable = false)
    private UserAccountEntity account;

    @Enumerated(EnumType.STRING)
    @Column(name = "TransactionType", nullable = false)
    private TransactionType transactionType;

    @Min(1)
    @Column(name = "Amount", nullable = false)
    private BigDecimal amount;

    @CreationTimestamp
    @Column(name = "TransactionDate", nullable = false)
    private Timestamp transactionDate;

    @Column(name = "TransactionDescription")
    private String TransactionDescription;
    public AccountTransactionsEntity() {
    }
    public AccountTransactionsEntity(UUID transactionId, UserAccountEntity account,
                                     TransactionType transactionType, BigDecimal amount, String transactionDescription) {
        this.transactionId = transactionId;
        this.account = account;
        this.transactionType = transactionType;
        this.amount = amount;
        this.TransactionDescription = transactionDescription;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public AccountTransactionsEntity setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public UserAccountEntity getAccount() {
        return account;
    }

    public AccountTransactionsEntity setAccount(UserAccountEntity account) {
        this.account = account;
        return this;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public AccountTransactionsEntity setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public AccountTransactionsEntity setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public Timestamp getTransactionDate() {
        return transactionDate;
    }

    public String getTransactionDescription() {
        return TransactionDescription;
    }

    public void setTransactionDescription(String transactionDescription) {
        TransactionDescription = transactionDescription;
    }
}
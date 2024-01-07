package com.example.bankingsystemspring.model;
import com.example.bankingsystemspring.common.TransactionType;
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

    @ManyToOne
    @JoinColumn(name = "DestinationAccountID")
    private UserAccountEntity destinationAccount;

    @CreationTimestamp
    @Column(name = "TransactionDate", nullable = false)
    private Timestamp transactionDate;
    public AccountTransactionsEntity(UUID transactionId, UserAccountEntity account,
                                     TransactionType transactionType, BigDecimal amount,
                                     UserAccountEntity destinationAccount) {
        this.transactionId = transactionId;
        this.account = account;
        this.transactionType = transactionType;
        this.amount = amount;
        this.destinationAccount = destinationAccount;
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

    public UserAccountEntity getDestinationAccount() {
        return destinationAccount;
    }

    public AccountTransactionsEntity setDestinationAccount(UserAccountEntity destinationAccount) {
        this.destinationAccount = destinationAccount;
        return this;
    }

    public Timestamp getTransactionDate() {
        return transactionDate;
    }
}
package com.example.bankingsystemspring.model.response;

import com.example.bankingsystemspring.common.enums.TransactionType;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

public class AccountTransactionTransferResponse extends AccountTransactionResponse {
    final private String destinationAccountChavePix;
    final private String destinationAccountHolderName;

    public AccountTransactionTransferResponse(UUID transactionId, Timestamp transactionDate, TransactionType transactionType, BigDecimal amount, BigDecimal balance, String destinationAccountChavePix, String destinationAccountHolderName) {
        super(transactionId, transactionDate, transactionType, amount, balance);
        this.destinationAccountChavePix = destinationAccountChavePix;
        this.destinationAccountHolderName = destinationAccountHolderName;
    }

    public String getDestinationAccountChavePix() {
        return destinationAccountChavePix;
    }

    public String getDestinationAccountHolderName() {
        return destinationAccountHolderName;
    }
}
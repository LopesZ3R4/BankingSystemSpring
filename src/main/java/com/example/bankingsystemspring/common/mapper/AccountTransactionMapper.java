package com.example.bankingsystemspring.common.mapper;

import com.example.bankingsystemspring.common.annotation.Mapper;
import com.example.bankingsystemspring.model.AccountTransactionsEntity;
import com.example.bankingsystemspring.model.response.AccountTransactionResponse;
import com.example.bankingsystemspring.model.response.AccountTransactionTransferResponse;
@Mapper
public class AccountTransactionMapper {

    public AccountTransactionResponse toAccountTransactionResponse(AccountTransactionsEntity accountTransactionsEntity) {

        return new AccountTransactionResponse(accountTransactionsEntity.getTransactionId(),
                accountTransactionsEntity.getTransactionDate(),
                accountTransactionsEntity.getTransactionType(),
                accountTransactionsEntity.getAmount(),
                accountTransactionsEntity.getAccount().getBalance());
    }

    public AccountTransactionTransferResponse toAccountTransactionTransferResponse(AccountTransactionsEntity accountTransactionsEntity) {

        return new AccountTransactionTransferResponse(accountTransactionsEntity.getTransactionId(),
                accountTransactionsEntity.getTransactionDate(),
                accountTransactionsEntity.getTransactionType(),
                accountTransactionsEntity.getAmount(),
                accountTransactionsEntity.getAccount().getBalance(),
                accountTransactionsEntity.getDestinationAccount().getChavePix(),
                accountTransactionsEntity.getDestinationAccount().getAccountHolderName());
    }
}
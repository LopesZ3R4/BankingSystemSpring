package com.example.bankingsystemspring.common.mapper;

import com.example.bankingsystemspring.common.annotation.Mapper;
import com.example.bankingsystemspring.common.enums.TransactionType;
import com.example.bankingsystemspring.model.AccountTransactionsEntity;
import com.example.bankingsystemspring.model.response.AccountTransactionResponse;
import com.example.bankingsystemspring.model.response.AccountTransactionTransferResponse;
@Mapper
public class AccountTransactionMapper {

    public AccountTransactionResponse toAccountTransactionResponse(AccountTransactionsEntity accountTransactionsEntity) {
        if (accountTransactionsEntity.getTransactionType() == TransactionType.transfer) {
            return new AccountTransactionTransferResponse(accountTransactionsEntity.getTransactionId(),
                    accountTransactionsEntity.getTransactionDate(),
                    accountTransactionsEntity.getTransactionType(),
                    accountTransactionsEntity.getAmount(),
                    accountTransactionsEntity.getAccount().getBalance(),
                    accountTransactionsEntity.getDestinationAccount().getChavePix(),
                    accountTransactionsEntity.getDestinationAccount().getAccountHolderName());
        } else {
            return new AccountTransactionResponse(accountTransactionsEntity.getTransactionId(),
                    accountTransactionsEntity.getTransactionDate(),
                    accountTransactionsEntity.getTransactionType(),
                    accountTransactionsEntity.getAmount(),
                    accountTransactionsEntity.getAccount().getBalance());
        }
    }
}
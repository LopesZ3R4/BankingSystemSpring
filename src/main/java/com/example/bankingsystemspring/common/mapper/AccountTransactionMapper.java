package com.example.bankingsystemspring.common.mapper;

import com.example.bankingsystemspring.common.annotation.Mapper;
import com.example.bankingsystemspring.model.AccountTransactionsEntity;
import com.example.bankingsystemspring.model.response.AccountTransactionResponse;

import java.math.BigDecimal;

@Mapper
public class AccountTransactionMapper {

    public AccountTransactionResponse toAccountTransactionResponse(AccountTransactionsEntity accountTransactionsEntity) {
            return new AccountTransactionResponse(accountTransactionsEntity.getTransactionId(),
                    accountTransactionsEntity.getTransactionDate(),
                    accountTransactionsEntity.getTransactionType(),
                    accountTransactionsEntity.getTransactionDescription(), accountTransactionsEntity.getAmount(),
                    BigDecimal.ZERO);
    }
}
package com.example.bankingsystemspring.service;

import com.example.bankingsystemspring.common.enums.TransactionType;
import com.example.bankingsystemspring.model.AccountTransactionsEntity;
import com.example.bankingsystemspring.model.UserAccountEntity;
import com.example.bankingsystemspring.repository.AccountTransactionRepository;
import com.example.bankingsystemspring.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;
    private final AccountTransactionRepository transactionRepository;

    @Autowired
    public UserAccountService(UserAccountRepository userAccountRepository, AccountTransactionRepository transactionRepository) {
        this.userAccountRepository = userAccountRepository;
        this.transactionRepository = transactionRepository;
    }

    public UserAccountEntity createUserAccount(UserAccountEntity userAccount) {
        userAccount.setAccountId(UUID.randomUUID());
        return userAccountRepository.save(userAccount);
    }

    public Optional<UserAccountEntity> findById(UUID accountId) {
        return userAccountRepository.findById(accountId);
    }
    public Optional<UserAccountEntity> findByPix(String chavePix) {
        return userAccountRepository.findByChavePix(chavePix);
    }
    public BigDecimal calculateBalance(UserAccountEntity account) {
        List<AccountTransactionsEntity> transactions = transactionRepository.findByAccount(account);
        BigDecimal balance = BigDecimal.ZERO;
        for (AccountTransactionsEntity transaction : transactions) {
            if (transaction.getTransactionType() == TransactionType.deposit) {
                balance = balance.add(transaction.getAmount());
            } else if (transaction.getTransactionType() == TransactionType.withdraw) {
                balance = balance.subtract(transaction.getAmount());
            }
        }
        return balance;
    }
}

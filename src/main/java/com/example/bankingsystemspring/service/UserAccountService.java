package com.example.bankingsystemspring.service;

import com.example.bankingsystemspring.model.AccountTransactionsEntity;
import com.example.bankingsystemspring.model.UserAccountEntity;
import com.example.bankingsystemspring.repository.UserAccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;

    @Autowired
    public UserAccountService(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    public UserAccountEntity createUserAccount(UserAccountEntity userAccount) {
        userAccount.setAccountId(UUID.randomUUID());
        return userAccountRepository.save(userAccount);
    }

    @Transactional
    public void processTransaction(AccountTransactionsEntity transaction){
        UserAccountEntity originAccount = transaction.getAccount();
        UserAccountEntity destinationAccount = transaction.getDestinationAccount();
        BigDecimal amount = transaction.getAmount();

        switch (transaction.getTransactionType()) {
            case deposit:
                originAccount.setBalance(originAccount.getBalance().add(amount));
                userAccountRepository.save(originAccount);
                return;
            case withdraw:
                originAccount.setBalance(originAccount.getBalance().subtract(amount));
                userAccountRepository.save(originAccount);
                return;
            case transfer:
                originAccount.setBalance(originAccount.getBalance().subtract(amount));
                destinationAccount.setBalance(destinationAccount.getBalance().add(amount));
                userAccountRepository.save(destinationAccount);
                userAccountRepository.save(originAccount);
                return;
            default:
                throw new IllegalArgumentException("Invalid transaction type");
        }
    }

    public Optional<UserAccountEntity> findById(UUID accountId) {
        return userAccountRepository.findById(accountId);
    }
    public Optional<UserAccountEntity> findByPix(String chavePix) {
        return userAccountRepository.findByChavePix(chavePix);
    }
}

package com.example.bankingsystemspring.service;

import com.example.bankingsystemspring.model.UserAccountEntity;
import com.example.bankingsystemspring.repository.UserAccountRepository;
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
    public UserAccountEntity withdraw(UserAccountEntity userAccount, BigDecimal amount) {
        if (userAccount.getBalance().compareTo(amount) < 0) {
            return null;
        }
        userAccount.setBalance(userAccount.getBalance().subtract(amount));
        userAccountRepository.save(userAccount);
        return userAccountRepository.save(userAccount);
     }
    public UserAccountEntity deposit(UserAccountEntity userAccount, BigDecimal amount) {
        userAccount.setBalance(userAccount.getBalance().add(amount));
        return userAccountRepository.save(userAccount);
    }

    public Optional<UserAccountEntity> findById(UUID accountId) {
        return userAccountRepository.findById(accountId);
    }
}

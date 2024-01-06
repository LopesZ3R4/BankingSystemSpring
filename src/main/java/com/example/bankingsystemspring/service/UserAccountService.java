package com.example.bankingsystemspring.service;

import com.example.bankingsystemspring.model.UserAccountEntity;
import com.example.bankingsystemspring.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}

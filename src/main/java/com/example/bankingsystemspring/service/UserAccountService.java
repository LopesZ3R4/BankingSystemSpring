package com.example.bankingsystemspring.service;

import com.example.bankingsystemspring.common.enums.TransactionType;
import com.example.bankingsystemspring.common.mapper.UserAccountMapper;
import com.example.bankingsystemspring.model.UserAccountEntity;
import com.example.bankingsystemspring.model.request.UserAccountRequest;
import com.example.bankingsystemspring.repository.AccountTransactionRepository;
import com.example.bankingsystemspring.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;
    private final AccountTransactionRepository transactionRepository;
    private final UserAccountMapper userAccountMapper;

    @Autowired
    public UserAccountService(UserAccountRepository userAccountRepository, AccountTransactionRepository transactionRepository, UserAccountMapper userAccountMapper) {
        this.userAccountRepository = userAccountRepository;
        this.transactionRepository = transactionRepository;
        this.userAccountMapper = userAccountMapper;
    }

    public UserAccountEntity createUserAccount(UserAccountRequest accountRequest) {
        UserAccountEntity newUserAccount = userAccountMapper.toUserAccountEntity(accountRequest);
        newUserAccount.setAccountId(UUID.randomUUID());
        return userAccountRepository.save(newUserAccount);
    }

    public Optional<UserAccountEntity> findById(UUID accountId) {
        return userAccountRepository.findById(accountId);
    }
    public Optional<UserAccountEntity> findByPix(String chavePix) {
        return userAccountRepository.findByChavePix(chavePix);
    }
    public Optional<UserAccountEntity> findByCPF(String cpf) {
        return userAccountRepository.findByCPF(cpf);
    }
    public BigDecimal calculateBalance(UserAccountEntity account) {
        return transactionRepository.findByAccount(account).stream()
                .map(transaction -> transaction.getTransactionType() == TransactionType.deposit
                        ? transaction.getAmount()
                        : transaction.getAmount().negate())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

package com.example.bankingsystemspring.service;

import com.example.bankingsystemspring.model.UserAccountEntity;
import com.example.bankingsystemspring.repository.UserAccountRepository;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserAccountServiceTest {
    private final EasyRandom random = new EasyRandom();

    @Mock
    private UserAccountRepository userAccountRepository;

    @InjectMocks
    private UserAccountService userAccountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUserAccount() {
        UserAccountEntity userAccount = new UserAccountEntity();
        userAccount.setAccountId(UUID.randomUUID());
        userAccount.setBalance(BigDecimal.ONE);

        when(userAccountRepository.save(any(UserAccountEntity.class))).thenReturn(userAccount);

        UserAccountEntity result = userAccountService.createUserAccount(userAccount);

        assertNotNull(result);
        assertEquals(userAccount, result);
    }

    @Test
    void testWithdraw_SufficientBalance() {
        UserAccountEntity userAccount = new UserAccountEntity();
        userAccount.setAccountId(UUID.randomUUID());
        userAccount.setBalance(new BigDecimal("100"));

        BigDecimal amount = new BigDecimal("50");

        when(userAccountRepository.save(any(UserAccountEntity.class))).thenReturn(userAccount);

        UserAccountEntity result = userAccountService.withdraw(userAccount, amount);

        assertNotNull(result);
        assertEquals(new BigDecimal("50"), result.getBalance());
    }

    @Test
    void testWithdraw_InsufficientBalance() {
        UserAccountEntity userAccount = new UserAccountEntity();
        userAccount.setAccountId(UUID.randomUUID());
        userAccount.setBalance(new BigDecimal("50"));

        BigDecimal amount = new BigDecimal("100");

        UserAccountEntity result = userAccountService.withdraw(userAccount, amount);

        assertNull(result);
    }
    @Test
    void deposit() {
        UserAccountEntity userAccount = random.nextObject(UserAccountEntity.class);
        BigDecimal accountBalance = userAccount.getBalance();

        BigDecimal amount = new BigDecimal("500");

        when(userAccountRepository.save(any(UserAccountEntity.class))).thenReturn(userAccount);

        UserAccountEntity result = userAccountService.deposit(userAccount, amount);
        BigDecimal newBalance = accountBalance.add(amount);

        assertNotNull(result);
        assertEquals(newBalance, result.getBalance());
    }

    @Test
    void cantFindById() {
        UUID accountId = UUID.randomUUID();
        when(userAccountRepository.findById(accountId)).thenReturn(Optional.empty());

        Optional<UserAccountEntity> result = userAccountService.findById(accountId);

        assertFalse(result.isPresent(), "No account should be found");
    }

    @Test
    void FindById() {
        UserAccountEntity userAccount = random.nextObject(UserAccountEntity.class);
        UUID accountId = UUID.randomUUID();
        userAccount.setAccountId(accountId);

        when(userAccountRepository.findById(accountId)).thenReturn(Optional.of(userAccount));

        Optional<UserAccountEntity> result = userAccountService.findById(accountId);

        assertTrue(result.isPresent(), "Account should be found");
        assertEquals(userAccount, result.get(), "Returned account should match the mock account");
    }
}
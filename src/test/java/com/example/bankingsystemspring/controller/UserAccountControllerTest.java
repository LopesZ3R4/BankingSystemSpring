package com.example.bankingsystemspring.controller;

import com.example.bankingsystemspring.model.UserAccountEntity;
import com.example.bankingsystemspring.service.UserAccountService;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserAccountController.class)
class UserAccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserAccountService userAccountService;

    @Autowired
    private UserAccountController userAccountController;
    private final EasyRandom random = new EasyRandom();

    private UserAccountEntity setupCreateUserAccount() {
        openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userAccountController).build();
        UserAccountEntity newAccount = random.nextObject(UserAccountEntity.class);

        Mockito.when(userAccountService.createUserAccount(Mockito.any(UserAccountEntity.class)))
                .thenReturn(newAccount);

        return newAccount;
    }

    @Test
    void createUserAccount() throws Exception {
        UserAccountEntity request = setupCreateUserAccount();

        String requestJson = String.format("{\"chavePix\": \"%s\", \"accountHolderName\": \"%s\", \"balance\": \"%s\"}",
                request.getChavePix(), request.getAccountHolderName(), request.getBalance());

        mockMvc.perform(post("/api/useraccount")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accountId").isNotEmpty())
                .andExpect(jsonPath("$.chavePix").value(request.getChavePix()))
                .andExpect(jsonPath("$.accountHolderName").value(request.getAccountHolderName()))
                .andExpect(jsonPath("$.balance").value(request.getBalance()));
        Mockito.verify(userAccountService, Mockito.times(1)).createUserAccount(any(UserAccountEntity.class));
    }

    @Test
    void deposit() throws Exception {
        UUID accountId = UUID.randomUUID();
        BigDecimal amount = BigDecimal.valueOf(100);
        UserAccountEntity userAccount = new UserAccountEntity();
        userAccount.setAccountId(accountId);
        userAccount.setBalance(BigDecimal.ZERO.add(amount));

        Mockito.when(userAccountService.findById(accountId)).thenReturn(Optional.of(userAccount));
        Mockito.when(userAccountService.deposit(userAccount, amount)).thenReturn(userAccount);

        mockMvc.perform(post("/api/useraccount/deposit/" + accountId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(amount));
        Mockito.verify(userAccountService, Mockito.times(1)).findById(any(UUID.class));
        Mockito.verify(userAccountService, Mockito.times(1)).deposit(any(UserAccountEntity.class),
                any(BigDecimal.class));
    }

    @Test
    void depositNegativeAmount() throws Exception {
        UUID accountId = UUID.randomUUID();
        BigDecimal amount = BigDecimal.valueOf(-100);

        mockMvc.perform(post("/api/useraccount/deposit/" + accountId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(amount)))
                .andExpect(status().isUnprocessableEntity());
        Mockito.verify(userAccountService, Mockito.times(0)).findById(any(UUID.class));
        Mockito.verify(userAccountService, Mockito.times(0)).deposit(any(UserAccountEntity.class),
                any(BigDecimal.class));
    }

    @Test
    void depositNonExistentAccount() throws Exception {
        UUID accountId = UUID.randomUUID();
        BigDecimal amount = BigDecimal.valueOf(100);

        Mockito.when(userAccountService.findById(accountId)).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/useraccount/deposit/" + accountId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(amount)))
                .andExpect(status().isNotFound());

        Mockito.verify(userAccountService, Mockito.times(1)).findById(any(UUID.class));
        Mockito.verify(userAccountService, Mockito.times(0)).deposit(any(UserAccountEntity.class),
                any(BigDecimal.class));
    }

    @Test
    void withdraw() {
    }
}
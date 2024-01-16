package com.example.bankingsystemspring.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.*;

import com.example.bankingsystemspring.common.enums.TransactionType;
import com.example.bankingsystemspring.common.mapper.AccountTransactionMapper;
import com.example.bankingsystemspring.model.AccountTransactionsEntity;
import com.example.bankingsystemspring.model.UserAccountEntity;
import com.example.bankingsystemspring.model.request.AccountTransactionRequest;
import com.example.bankingsystemspring.model.response.AccountTransactionResponse;
import com.example.bankingsystemspring.service.AccountTransactionService;
import com.example.bankingsystemspring.service.UserAccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public class AccountTransactionControllerTest {

    @Mock
    private AccountTransactionService accountTransactionService;

    @Mock
    private UserAccountService userAccountService;

    @Mock
    private AccountTransactionMapper mapper;

    @InjectMocks
    private AccountTransactionController accountTransactionController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetTransactionById_Found() {
        UUID transactionId = UUID.randomUUID();
        AccountTransactionsEntity transactionEntity = new AccountTransactionsEntity();
        AccountTransactionResponse expectedResponse = new AccountTransactionResponse();

        when(accountTransactionService.getTransactionById(transactionId)).thenReturn(transactionEntity);
        when(mapper.toAccountTransactionResponse(transactionEntity)).thenReturn(expectedResponse);

        ResponseEntity<Object> response = accountTransactionController.getTransactionById(transactionId);

        assertEquals(OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());

        verify(accountTransactionService).getTransactionById(transactionId);
        verify(mapper).toAccountTransactionResponse(transactionEntity);
    }

    @Test
    public void testGetTransactionById_NotFound() {
        UUID transactionId = UUID.randomUUID();
        when(accountTransactionService.getTransactionById(transactionId)).thenReturn(null);

        ResponseEntity<Object> response = accountTransactionController.getTransactionById(transactionId);

        assertEquals(NOT_FOUND, response.getStatusCode());
    }

//    @Test
//    public void testGetTransactionsByAccount_Exists() {
//        UUID accountId = UUID.randomUUID();
//        UserAccountEntity userAccount = new UserAccountEntity();
//        List<AccountTransactionsEntity> transactions = Collections.emptyList();
//
//        when(userAccountService.findById(accountId)).thenReturn(Optional.of(userAccount));
//        when(accountTransactionService.getTransactionsByAccount(userAccount)).thenReturn(transactions);
//
//        ResponseEntity<List<AccountTransactionResponse>> response = accountTransactionController
//                .getTransactionsByAccount(accountId);
//
//        assertEquals(OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//    }

//    @Test
//    public void testGetTransactionsByAccount_NotExists() {
//        UUID accountId = UUID.randomUUID();
//        when(userAccountService.findById(accountId)).thenReturn(Optional.empty());
//
//        ResponseEntity<List<AccountTransactionResponse>> response = accountTransactionController
//                .getTransactionsByAccount(accountId);
//
//        assertEquals(NOT_FOUND, response.getStatusCode());
//    }

    @Test
    public void testDeposit_Successful() {
        UUID accountId = UUID.randomUUID();
        BigDecimal amount = new BigDecimal("100");
        AccountTransactionsEntity transaction = new AccountTransactionsEntity();
        when(userAccountService.findById(accountId)).thenReturn(Optional.of(new UserAccountEntity()));
        when(accountTransactionService.createAccountTransaction(any(AccountTransactionRequest.class), eq(TransactionType.deposit)))
                .thenReturn(transaction);
        when(mapper.toAccountTransactionResponse(transaction)).thenReturn(new AccountTransactionResponse());

        ResponseEntity<AccountTransactionResponse> response = accountTransactionController.deposit(accountId, amount);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testWithdraw_Successful() {
        UUID accountId = UUID.randomUUID();
        BigDecimal amount = new BigDecimal("50");
        AccountTransactionsEntity transaction = new AccountTransactionsEntity();
        when(userAccountService.findById(accountId)).thenReturn(Optional.of(new UserAccountEntity()));
        when(accountTransactionService.createAccountTransaction(any(AccountTransactionRequest.class), eq(TransactionType.withdraw)))
                .thenReturn(transaction);
        when(mapper.toAccountTransactionResponse(transaction)).thenReturn(new AccountTransactionResponse());

        ResponseEntity<AccountTransactionResponse> response = accountTransactionController.withdraw(accountId, amount);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testTransaction_Successful() {
        UUID accountId = UUID.randomUUID();
        String pixKey = "chavePix";
        BigDecimal amount = new BigDecimal("100.00");
        AccountTransactionRequest request = new AccountTransactionRequest(accountId, amount, pixKey);
        AccountTransactionsEntity transaction = new AccountTransactionsEntity();
        AccountTransactionResponse transactionResponse = new AccountTransactionResponse();

        UserAccountEntity userAccountEntity = new UserAccountEntity();
        userAccountEntity.setAccountId(accountId);
        UserAccountEntity pixUserAccountEntity = new UserAccountEntity();
        pixUserAccountEntity.setAccountId(UUID.randomUUID());

        when(userAccountService.findById(accountId)).thenReturn(Optional.of(userAccountEntity));
        when(userAccountService.findByPix(pixKey)).thenReturn(Optional.of(pixUserAccountEntity));

        when(accountTransactionService.createAccountTransaction(request, TransactionType.transfer))
                .thenReturn(transaction);

        when(mapper.toAccountTransactionResponse(transaction)).thenReturn(transactionResponse);

        ResponseEntity<AccountTransactionResponse> response = accountTransactionController.Transaction(request);

        assertEquals(CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testTransaction_NotExistingOriginAccount() {
        UUID accountId = UUID.randomUUID();
        String pixKey = "existingPixKey";
        BigDecimal amount = new BigDecimal("100.00");
        AccountTransactionRequest request = new AccountTransactionRequest(accountId, amount, pixKey);

        when(userAccountService.findById(accountId)).thenReturn(Optional.empty());

        ResponseEntity<AccountTransactionResponse> response = accountTransactionController.Transaction(request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testTransaction_NotExistingDestinationAccount() {
        UUID accountId = UUID.randomUUID();
        String pixKey = "nonExistingPixKey";
        BigDecimal amount = new BigDecimal("100.00");
        AccountTransactionRequest request = new AccountTransactionRequest(accountId, amount, pixKey);

        when(userAccountService.findById(accountId)).thenReturn(Optional.of(new UserAccountEntity()));
        when(userAccountService.findByPix(pixKey)).thenReturn(Optional.empty());

        ResponseEntity<AccountTransactionResponse> response = accountTransactionController.Transaction(request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testTransaction_OriginSameAsDestinationAccount() {
        UUID accountId = UUID.randomUUID();
        BigDecimal amount = new BigDecimal("100.00");
        AccountTransactionRequest request = new AccountTransactionRequest(accountId, amount, "samePixKey");

        UserAccountEntity sameAccount = new UserAccountEntity();
        sameAccount.setAccountId(accountId);

        when(userAccountService.findById(accountId)).thenReturn(Optional.of(sameAccount));
        when(userAccountService.findByPix("samePixKey")).thenReturn(Optional.of(sameAccount));

        ResponseEntity<AccountTransactionResponse> response = accountTransactionController.Transaction(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    @Test
    public void testDeposit_NegativeAmount() {
        UUID accountId = UUID.randomUUID();
        BigDecimal negativeAmount = new BigDecimal("-50");

        when(userAccountService.findById(accountId)).thenReturn(Optional.of(new UserAccountEntity()));

        ResponseEntity<AccountTransactionResponse> response = accountTransactionController.deposit(accountId, negativeAmount);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }


    @Test
    public void testWithdraw_NegativeAmount() {
        UUID accountId = UUID.randomUUID();
        BigDecimal negativeAmount = new BigDecimal("-50");
        when(userAccountService.findById(accountId)).thenReturn(Optional.of(new UserAccountEntity()));

        ResponseEntity<AccountTransactionResponse> response = accountTransactionController.withdraw(accountId, negativeAmount);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    @Test
    public void testTransfer_NegativeAmount() {
        UUID accountId = UUID.randomUUID();
        String pixKey = "validPixKey";
        BigDecimal negativeAmount = new BigDecimal("-50");
        AccountTransactionRequest request = new AccountTransactionRequest(accountId, negativeAmount, pixKey);

        UserAccountEntity originAccount = new UserAccountEntity();
        originAccount.setAccountId(accountId);
        UserAccountEntity destinationAccount = new UserAccountEntity();
        destinationAccount.setAccountId(UUID.randomUUID());

        when(userAccountService.findById(accountId)).thenReturn(Optional.of(originAccount));
        when(userAccountService.findByPix(pixKey)).thenReturn(Optional.of(destinationAccount));

        ResponseEntity<AccountTransactionResponse> response = accountTransactionController.Transaction(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testTransfer_Successful() {
        UUID originAccountId = UUID.randomUUID();
        UUID destinationAccountId = UUID.randomUUID();
        String pixKey = "validPixKey";
        BigDecimal amount = new BigDecimal("75");
        AccountTransactionRequest request = new AccountTransactionRequest(originAccountId, amount, pixKey);
        AccountTransactionsEntity transaction = new AccountTransactionsEntity();

        UserAccountEntity originAccountEntity = new UserAccountEntity();
        originAccountEntity.setAccountId(originAccountId);
        UserAccountEntity destinationAccountEntity = new UserAccountEntity();
        destinationAccountEntity.setAccountId(destinationAccountId);

        when(userAccountService.findById(originAccountId)).thenReturn(Optional.of(originAccountEntity));
        when(userAccountService.findByPix(pixKey)).thenReturn(Optional.of(destinationAccountEntity));
        when(accountTransactionService.createAccountTransaction(request, TransactionType.transfer)).thenReturn(transaction);
        when(mapper.toAccountTransactionResponse(transaction)).thenReturn(new AccountTransactionResponse());

        ResponseEntity<AccountTransactionResponse> response = accountTransactionController.Transaction(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }


    @Test
    public void testTransfer_SameOriginAndDestination() {
        UUID accountId = UUID.randomUUID();
        BigDecimal amount = new BigDecimal("50");
        AccountTransactionRequest request = new AccountTransactionRequest(accountId, amount, accountId.toString());
        UserAccountEntity accountEntity = new UserAccountEntity();
        accountEntity.setAccountId(accountId);

        when(userAccountService.findById(accountId)).thenReturn(Optional.of(accountEntity));
        when(userAccountService.findByPix(accountId.toString())).thenReturn(Optional.of(accountEntity));

        ResponseEntity<AccountTransactionResponse> response = accountTransactionController.Transaction(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }



}
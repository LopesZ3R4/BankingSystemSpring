package com.example.bankingsystemspring.controller;

import com.example.bankingsystemspring.common.enums.TransactionType;
import com.example.bankingsystemspring.common.mapper.AccountTransactionMapper;
import com.example.bankingsystemspring.common.mapper.UserAccountMapper;
import com.example.bankingsystemspring.model.AccountTransactionsEntity;
import com.example.bankingsystemspring.model.UserAccountEntity;
import com.example.bankingsystemspring.model.request.AccountTransactionRequest;
import com.example.bankingsystemspring.model.response.AccountTransactionResponse;
import com.example.bankingsystemspring.model.response.AccountTransactionSummary;
import com.example.bankingsystemspring.model.response.UserAccountResponse;
import com.example.bankingsystemspring.service.AccountTransactionService;
import com.example.bankingsystemspring.service.UserAccountService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/api/transactions")
public class AccountTransactionController {

    private final AccountTransactionService accountTransactionService;
    private final UserAccountService userAccountService;
    private final AccountTransactionMapper accountTransactionMapper;

    @Autowired
    public AccountTransactionController(AccountTransactionService accountTransactionService,
                                        UserAccountService userAccountService, AccountTransactionMapper accountTransactionMapper) {
        this.accountTransactionService = accountTransactionService;
        this.userAccountService = userAccountService;
        this.accountTransactionMapper = accountTransactionMapper;
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<Object> getTransactionById(@PathVariable UUID transactionId) {
        Optional<AccountTransactionsEntity> transaction = Optional
                .ofNullable(accountTransactionService.getTransactionById(transactionId));
        if (transaction.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        AccountTransactionResponse response = accountTransactionMapper.toAccountTransactionResponse(transaction.get());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<AccountTransactionSummary> getTransactionsByAccount(@PathVariable UUID accountId) {
        Optional<UserAccountEntity> userAccount = userAccountService.findById(accountId);

        if (userAccount.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<AccountTransactionResponse> transactions = accountTransactionService.getTransactionsByAccount(userAccount.get())
                .stream()
                .map(accountTransactionMapper::toAccountTransactionResponse)
                .toList();

        BigDecimal userAccountBalance = userAccountService.calculateBalance(userAccount.get());

        AccountTransactionSummary transactionSummary = new AccountTransactionSummary(
                userAccount.get().getAccountId(),
                userAccount.get().getChavePix(),
                userAccount.get().getAccountHolderName(),
                userAccountBalance,
                transactions
        );
        return ResponseEntity.ok(transactionSummary);
    }

    @PostMapping("deposit/{accountId}")
    public ResponseEntity<AccountTransactionResponse> deposit(@PathVariable @NotNull UUID accountId,
            @RequestBody @NotNull @Min(1) BigDecimal amount) {
        Optional<UserAccountEntity> originAccount = userAccountService.findById(accountId);

        if (originAccount.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        AccountTransactionRequest transactionRequest = new AccountTransactionRequest(originAccount.get().getAccountId(),amount);

        AccountTransactionsEntity transaction = accountTransactionService.createAccountTransaction(transactionRequest,
                TransactionType.deposit);

        if (transaction == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String uri = String.format("transactions/%s", transaction.getTransactionId());
        AccountTransactionResponse response = accountTransactionMapper.toAccountTransactionResponse(transaction);

        response.setBalance(userAccountService.calculateBalance(originAccount.get()));

        return ResponseEntity.created(URI.create(uri)).body(response);
    }

    @PostMapping("withdraw/{accountId}")
    public ResponseEntity<AccountTransactionResponse> withdraw(@PathVariable @NotNull UUID accountId,
            @RequestBody @NotNull @Min(1) BigDecimal amount) {
        Optional<UserAccountEntity> originAccount = userAccountService.findById(accountId);

        if (originAccount.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        AccountTransactionRequest transactionRequest = new AccountTransactionRequest(originAccount.get().getAccountId(),amount);

        AccountTransactionsEntity transaction = accountTransactionService.createAccountTransaction(transactionRequest,
                TransactionType.withdraw);

        if (transaction == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String uri = String.format("transactions/%s", transaction.getTransactionId());
        AccountTransactionResponse response = accountTransactionMapper.toAccountTransactionResponse(transaction);

        response.setBalance(userAccountService.calculateBalance(originAccount.get()));

        return ResponseEntity.created(URI.create(uri)).body(response);
    }

    @PostMapping("transfer")
    public ResponseEntity<AccountTransactionResponse> Transaction(@RequestBody @NotNull AccountTransactionRequest transactionRequest) {
        Optional<UserAccountEntity> originAccount = userAccountService.findById(transactionRequest.getAccountId());
        Optional<UserAccountEntity> destinationAccount = userAccountService.findByPix(transactionRequest.getChavePix());

        if (originAccount.isEmpty() || destinationAccount.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (originAccount.get().getAccountId().equals(destinationAccount.get().getAccountId())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        AccountTransactionsEntity transaction = accountTransactionService.createAccountTransaction(transactionRequest,
                TransactionType.transfer);

        if (transaction == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String uri = String.format("transactions/%s", transaction.getTransactionId());
        AccountTransactionResponse response = accountTransactionMapper.toAccountTransactionResponse(transaction);

        return ResponseEntity.created(URI.create(uri)).body(response);
    }
}
package com.example.bankingsystemspring.controller;

import com.example.bankingsystemspring.common.enums.TransactionType;
import com.example.bankingsystemspring.common.mapper.AccountTransactionMapper;
import com.example.bankingsystemspring.model.AccountTransactionsEntity;
import com.example.bankingsystemspring.model.UserAccountEntity;
import com.example.bankingsystemspring.model.request.AccountTransactionRequest;
import com.example.bankingsystemspring.model.response.AccountTransactionResponse;
import com.example.bankingsystemspring.model.response.AccountTransactionTransferResponse;
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
    private final AccountTransactionMapper mapper;

    @Autowired
    public AccountTransactionController(AccountTransactionService accountTransactionService,
                                        UserAccountService userAccountService, AccountTransactionMapper mapper) {
        this.accountTransactionService = accountTransactionService;
        this.userAccountService = userAccountService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<AccountTransactionsEntity>> getAllTransactions() {
        return ResponseEntity.ok(accountTransactionService.getAllTransactions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getTransactionById(@PathVariable UUID id) {
        Optional<AccountTransactionsEntity> transaction = Optional
                .ofNullable(accountTransactionService.getTransactionById(id));
        if(transaction.isEmpty()){
            ResponseEntity.notFound();
        }
        AccountTransactionResponse response;
        if(transaction.get().getTransactionType() == TransactionType.transfer){
            response = mapper.toAccountTransactionTransferResponse(transaction.get());
        } else {
            response = mapper.toAccountTransactionResponse(transaction.get());
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("deposit/{accountId}")
    public ResponseEntity<AccountTransactionResponse> deposit(@PathVariable @NotNull UUID accountId,
            @RequestBody @NotNull @Min(1) BigDecimal amount) {
        Optional<UserAccountEntity> originAccount = userAccountService.findById(accountId);

        if (originAccount.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        AccountTransactionRequest transactionRequest = new AccountTransactionRequest(originAccount.get().getAccountId(),
                amount);

        AccountTransactionsEntity transaction = accountTransactionService.createAccountTransaction(transactionRequest,
                TransactionType.deposit);

        if (transaction == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userAccountService.processTransaction(transaction);

        String uri = String.format("transactions/%s", transaction.getTransactionId());
        AccountTransactionResponse response = mapper.toAccountTransactionResponse(transaction);

        return ResponseEntity.created(URI.create(uri)).body(response);
    }
    @PostMapping("withdraw/{accountId}")
    public ResponseEntity<AccountTransactionResponse> withdraw(@PathVariable @NotNull UUID accountId,
                                                             @RequestBody @NotNull @Min(1) BigDecimal amount) {
        Optional<UserAccountEntity> originAccount = userAccountService.findById(accountId);

        if (originAccount.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        AccountTransactionRequest transactionRequest = new AccountTransactionRequest(originAccount.get().getAccountId(),
                amount);

        AccountTransactionsEntity transaction = accountTransactionService.createAccountTransaction(transactionRequest,
                TransactionType.withdraw);

        if (transaction == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userAccountService.processTransaction(transaction);

        String uri = String.format("transactions/%s", transaction.getTransactionId());
        AccountTransactionResponse response = mapper.toAccountTransactionResponse(transaction);

        return ResponseEntity.created(URI.create(uri)).body(response);
    }

    @PostMapping("transfer")
    public ResponseEntity<AccountTransactionTransferResponse> Transaction(
            @RequestBody AccountTransactionRequest transactionRequest) {
        Optional<UserAccountEntity> originAccount = userAccountService.findById(transactionRequest.getAccountId());
        Optional<UserAccountEntity> destinationAccount = userAccountService.findByPix(transactionRequest.getChavePix());

        if (originAccount.isEmpty() || destinationAccount.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        AccountTransactionsEntity transaction = accountTransactionService.createAccountTransaction(transactionRequest,
                TransactionType.transfer);

        if (transaction == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userAccountService.processTransaction(transaction);

        String uri = String.format("transactions/%s", transaction.getTransactionId());
        AccountTransactionTransferResponse response = mapper.toAccountTransactionTransferResponse(transaction);

        return ResponseEntity.created(URI.create(uri)).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable UUID id) {
        accountTransactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }
}
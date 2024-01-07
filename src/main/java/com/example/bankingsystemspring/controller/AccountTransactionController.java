package com.example.bankingsystemspring.controller;

import com.example.bankingsystemspring.common.TransactionType;
import com.example.bankingsystemspring.model.AccountTransactionsEntity;
import com.example.bankingsystemspring.model.UserAccountEntity;
import com.example.bankingsystemspring.model.request.AccountTransactionRequest;
import com.example.bankingsystemspring.service.AccountTransactionService;
import com.example.bankingsystemspring.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
public class AccountTransactionController {

  private final AccountTransactionService accountTransactionService;
  private final UserAccountService userAccountService;

  @Autowired
  public AccountTransactionController(AccountTransactionService accountTransactionService, UserAccountService userAccountService) {
      this.accountTransactionService = accountTransactionService;
      this.userAccountService = userAccountService;
  }

  @GetMapping
  public ResponseEntity<List<AccountTransactionsEntity>> getAllTransactions() {
      return ResponseEntity.ok(accountTransactionService.getAllTransactions());
  }

  @GetMapping("/{id}")
  public ResponseEntity<AccountTransactionsEntity> getTransactionById(@PathVariable UUID id) {
      Optional<AccountTransactionsEntity> transaction = Optional.ofNullable(accountTransactionService.getTransactionById(id));
      return transaction.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<AccountTransactionsEntity> newTransaction(@RequestBody AccountTransactionRequest transactionRequest) {
      Optional<UserAccountEntity> originAccount = userAccountService.findById(transactionRequest.getAccountId());

      if (originAccount.isEmpty()){
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
      UserAccountEntity destinationAccount = userAccountService.findById(transactionRequest.getDestinationAccountId()).orElse(null);

      if (transactionRequest.getTransactionType() == TransactionType.transfer && destinationAccount == null){
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }

      AccountTransactionsEntity transaction = accountTransactionService.createAccountTransaction(transactionRequest);

      if(transaction == null){
          return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
      userAccountService.processTransaction(transaction.getAccount(), transaction.getDestinationAccount(), transaction.getTransactionType(), transaction.getAmount());

      String uri = String.format("transactions/%s",transaction.getTransactionId());
      return ResponseEntity.created(URI.create(uri)).body(transaction);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTransaction(@PathVariable UUID id) {
      accountTransactionService.deleteTransaction(id);
      return ResponseEntity.noContent().build();
  }
}
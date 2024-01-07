package com.example.bankingsystemspring.service;

import com.example.bankingsystemspring.common.enums.TransactionType;
import com.example.bankingsystemspring.model.AccountTransactionsEntity;
import com.example.bankingsystemspring.model.UserAccountEntity;
import com.example.bankingsystemspring.model.request.AccountTransactionRequest;
import com.example.bankingsystemspring.repository.AccountTransactionRepository;
import com.example.bankingsystemspring.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountTransactionService {

   private final AccountTransactionRepository accountTransactionRepository;
   private final UserAccountRepository userAccountRepository;

   @Autowired
   public AccountTransactionService(AccountTransactionRepository accountTransactionRepository, UserAccountRepository userAccountRepository) {
       this.accountTransactionRepository = accountTransactionRepository;
       this.userAccountRepository = userAccountRepository;
   }
   public AccountTransactionsEntity createAccountTransaction (AccountTransactionRequest transactionRequest, TransactionType transactionType) {
       UUID newTransactionID = UUID.randomUUID();
       Optional<UserAccountEntity> originAccount = userAccountRepository.findById(transactionRequest.getAccountId());

       if(transactionType != TransactionType.deposit && originAccount.get().getBalance().compareTo(transactionRequest.getAmount()) < 0) {
           return null;
       }

       UserAccountEntity destinationAccount = userAccountRepository.findByChavePix(transactionRequest.getChavePix()).orElse(null);

       AccountTransactionsEntity newTransaction = new AccountTransactionsEntity(newTransactionID,
               originAccount.get(),
               transactionType,
               transactionRequest.getAmount(),
               destinationAccount);


       return accountTransactionRepository.save(newTransaction);
   }

   public List<AccountTransactionsEntity> getAllTransactions() {
       return accountTransactionRepository.findAll();
   }

   public AccountTransactionsEntity getTransactionById(UUID id) {
       return accountTransactionRepository.findById(id).orElse(null);
   }

   public void deleteTransaction(UUID id) {
       accountTransactionRepository.deleteById(id);
   }
}
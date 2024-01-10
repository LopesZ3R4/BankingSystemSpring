package com.example.bankingsystemspring.service;

import com.example.bankingsystemspring.common.enums.TransactionType;
import com.example.bankingsystemspring.exception.InsufficientBalanceException;
import com.example.bankingsystemspring.model.AccountTransactionsEntity;
import com.example.bankingsystemspring.model.UserAccountEntity;
import com.example.bankingsystemspring.model.request.AccountTransactionRequest;
import com.example.bankingsystemspring.repository.AccountTransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AccountTransactionService {

   private final AccountTransactionRepository accountTransactionRepository;
   private final UserAccountService userAccountService;

   @Autowired
   public AccountTransactionService(AccountTransactionRepository accountTransactionRepository, UserAccountService userAccountService) {
       this.accountTransactionRepository = accountTransactionRepository;
       this.userAccountService = userAccountService;
   }
    private AccountTransactionsEntity createAndSaveTransaction(UserAccountEntity account, TransactionType transactionType, BigDecimal amount, String transactionDescription) {
       UUID newTransactionId = UUID.randomUUID();
       return accountTransactionRepository.save(new AccountTransactionsEntity(newTransactionId, account, transactionType, amount, transactionDescription));
    }
    @Transactional
    public AccountTransactionsEntity createAccountTransaction(AccountTransactionRequest transactionRequest, TransactionType transactionType) {
        if (transactionType == null) {
            throw new IllegalArgumentException("Transaction type cannot be null");
        }

        Optional<UserAccountEntity> originAccount = userAccountService.findById(transactionRequest.getAccountId());

        if(originAccount.isEmpty()){
            throw new EntityNotFoundException("Destination account not found");
        }
        BigDecimal originAccountBalance = userAccountService.calculateBalance(originAccount.get());

        if(transactionType != TransactionType.deposit && originAccountBalance.compareTo(transactionRequest.getAmount()) < 0) {
            throw new InsufficientBalanceException("Account does not have enough balance.");
        }

        if (transactionType != TransactionType.transfer) {
            String transactionDescription = transactionType == TransactionType.deposit
                    ? "Depósito"
                    : "Saque";

            return createAndSaveTransaction(originAccount.get(), transactionType, transactionRequest.getAmount(), transactionDescription);
        }

        UserAccountEntity destinationAccount = userAccountService.findByPix(transactionRequest.getChavePix())
                .orElseThrow(() -> new EntityNotFoundException("Destination account not found"));

        createAndSaveTransaction(originAccount.get(), TransactionType.withdraw, transactionRequest.getAmount(),"Transferencia para "+destinationAccount.getAccountHolderName());
        return createAndSaveTransaction(destinationAccount, TransactionType.deposit, transactionRequest.getAmount(), "Transferencia de "+originAccount.get().getAccountHolderName());
    }

    public List<AccountTransactionsEntity> getTransactionsByAccount(UserAccountEntity account) {
        List<AccountTransactionsEntity> transactionsFromAccount = accountTransactionRepository.findByAccount(account);

        return transactionsFromAccount.stream()
                .sorted(Comparator.comparing(AccountTransactionsEntity::getTransactionDate).reversed())
                .collect(Collectors.toList());
    }

   public AccountTransactionsEntity getTransactionById(UUID id) {
       return accountTransactionRepository.findById(id).orElse(null);
   }

   public void deleteTransaction(UUID id) {
       accountTransactionRepository.deleteById(id);
   }
}
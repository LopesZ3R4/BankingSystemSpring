package com.example.bankingsystemspring.controller;

import com.example.bankingsystemspring.model.UserAccountEntity;
import com.example.bankingsystemspring.service.UserAccountService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
@Validated
@RestController
@RequestMapping("/api/useraccount")
public class UserAccountController {

    private final UserAccountService userAccountService;

    @Autowired
    public UserAccountController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @PostMapping
    public UserAccountEntity createUserAccount(@RequestBody UserAccountEntity userAccount) {
        return userAccountService.createUserAccount(userAccount);
    }
    @PostMapping(value = "deposit/{accountId}")
    public ResponseEntity<Object> deposit(@PathVariable @NotNull UUID accountId,@RequestBody @NotNull @Min(1) BigDecimal amount) {
        Optional<UserAccountEntity> userAccount = userAccountService.findById(accountId);

        if (userAccount.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        UserAccountEntity updatedUserAccount = userAccountService.deposit(userAccount.get(),amount);

        if (updatedUserAccount == null) {
            return new ResponseEntity<>("Withdrawal failed due to insufficient balance", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(updatedUserAccount, HttpStatus.OK);
    }

    @PostMapping(value = "withdraw/{accountId}")
    public ResponseEntity<Object> withdraw(@PathVariable @NotNull UUID accountId, @RequestBody @NotNull @Min(1) BigDecimal amount) {

        Optional<UserAccountEntity> userAccount = userAccountService.findById(accountId);

        if (userAccount.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        UserAccountEntity updatedUserAccount = userAccountService.withdraw(userAccount.get(),amount);

        if (updatedUserAccount == null) {
            return new ResponseEntity<>("Withdrawal failed due to insufficient balance", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(updatedUserAccount, HttpStatus.OK);
    }
}
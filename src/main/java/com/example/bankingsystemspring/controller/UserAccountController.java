package com.example.bankingsystemspring.controller;

import com.example.bankingsystemspring.model.UserAccountEntity;
import com.example.bankingsystemspring.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;
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
    public ResponseEntity<Object> createUserAccount(@RequestBody UserAccountEntity userAccountRequest) {
        Optional<UserAccountEntity> existingPix = userAccountService.findByPix(userAccountRequest.getChavePix());

        if (existingPix.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("An account with the provided ChavePix already exists.");
        }

        UserAccountEntity userAccount = userAccountService.createUserAccount(userAccountRequest);
        String uri = String.format("useraccount/%s",userAccount.getAccountId());
        return ResponseEntity.created(URI.create(uri)).body(userAccount);
    }
}
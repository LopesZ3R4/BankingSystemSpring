package com.example.bankingsystemspring.controller;

import com.example.bankingsystemspring.model.UserAccountEntity;
import com.example.bankingsystemspring.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
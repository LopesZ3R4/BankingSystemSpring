package com.example.bankingsystemspring.controller;

import com.example.bankingsystemspring.common.mapper.UserAccountMapper;
import com.example.bankingsystemspring.model.UserAccountEntity;
import com.example.bankingsystemspring.model.request.UserAccountRequest;
import com.example.bankingsystemspring.model.response.UserAccountResponse;
import com.example.bankingsystemspring.service.UserAccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@Validated
@Tag(name = "Contas", description = "Operações de Cadastro e Consulta de Contas")
@RestController
@RequestMapping("/api/useraccount")
public class UserAccountController {

    private final UserAccountService userAccountService;
    private final UserAccountMapper userAccountMapper;

    @Autowired
    public UserAccountController(UserAccountService userAccountService, UserAccountMapper userAccountMapper) {
        this.userAccountService = userAccountService;
        this.userAccountMapper = userAccountMapper;
    }

    @PostMapping
    public ResponseEntity<Object> createUserAccount(@RequestBody @NotNull UserAccountRequest userAccountRequest) {
        Optional<UserAccountEntity> existingPix = userAccountService.findByPix(userAccountRequest.getChavePix());
        Optional<UserAccountEntity> existingCPF = userAccountService.findByCPF(userAccountRequest.getCPF());

        if (existingCPF.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Já existe uma conta com este CPF!");
        }
        if (existingPix.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Chave PIX indisponível para uso!");
        }

        UserAccountEntity userAccount = userAccountService.createUserAccount(userAccountRequest);
        UserAccountResponse userAccountResponse = userAccountMapper.toUserAccountResponse(userAccount);

        String uri = String.format("useraccount/%s",userAccount.getAccountId());
        return ResponseEntity.created(URI.create(uri)).body(userAccountResponse);
    }
    @GetMapping("/{accountId}")
    public ResponseEntity<UserAccountResponse> getUserAccount(@PathVariable @NotNull UUID accountId){
        Optional<UserAccountEntity> userAccount = userAccountService.findById(accountId);
        if (userAccount.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        BigDecimal balance = userAccountService.calculateBalance(userAccount.get());
        UserAccountResponse userAccountResponse = userAccountMapper.toUserAccountResponse(userAccount.get()).setBalance(balance);

        return ResponseEntity.ok(userAccountResponse);
    }
}
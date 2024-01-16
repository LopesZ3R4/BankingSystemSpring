package com.example.bankingsystemspring.model.request;

import jakarta.validation.constraints.Size;

public class UserAccountRequest {
    private final String chavePix;

    private final String accountHolderName;
    @Size(max = 12)
    private final String CPF;

    public UserAccountRequest(String chavePix, String accountHolderName, String cpf) {
        this.chavePix = chavePix;
        this.accountHolderName = accountHolderName;
        this.CPF = cpf;
    }

    public String getChavePix() {
        return chavePix;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public String getCPF() {
        return CPF;
    }
}
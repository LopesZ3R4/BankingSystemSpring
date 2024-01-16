package com.example.bankingsystemspring.common.mapper;

import com.example.bankingsystemspring.common.annotation.Mapper;
import com.example.bankingsystemspring.model.UserAccountEntity;
import com.example.bankingsystemspring.model.request.UserAccountRequest;
import com.example.bankingsystemspring.model.response.UserAccountResponse;
import jakarta.validation.constraints.NotNull;


import java.math.BigDecimal;

@Mapper
public class UserAccountMapper {
    public UserAccountResponse toUserAccountResponse(@NotNull UserAccountEntity userAccount){
        return new UserAccountResponse(
                userAccount.getAccountId(),
                userAccount.getChavePix(),
                userAccount.getAccountHolderName(),
                BigDecimal.ZERO
        );
    }
    public UserAccountEntity toUserAccountEntity(@NotNull UserAccountRequest userAccountRequest) {
        UserAccountEntity userAccount = new UserAccountEntity();

        userAccount.setAccountHolderName(userAccountRequest.getAccountHolderName());
        userAccount.setCPF(userAccountRequest.getCPF());
        userAccount.setChavePix(userAccountRequest.getChavePix());

        return userAccount;
    }
}

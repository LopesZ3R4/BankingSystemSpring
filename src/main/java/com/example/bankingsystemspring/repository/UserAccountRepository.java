package com.example.bankingsystemspring.repository;

import com.example.bankingsystemspring.model.UserAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserAccountRepository extends JpaRepository<UserAccountEntity, UUID> {

}
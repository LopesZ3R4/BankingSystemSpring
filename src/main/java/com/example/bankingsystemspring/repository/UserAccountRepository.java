package com.example.bankingsystemspring.repository;

import com.example.bankingsystemspring.model.UserAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccountEntity, Long> {

}
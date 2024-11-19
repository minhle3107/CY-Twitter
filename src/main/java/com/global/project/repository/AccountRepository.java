package com.global.project.repository;

import com.global.project.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUser_Username(String username);

    boolean existsByEmail(String email);
}

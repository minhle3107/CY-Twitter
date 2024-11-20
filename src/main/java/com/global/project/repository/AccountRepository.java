package com.global.project.repository;

import com.global.project.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUser_Username(String username);

    boolean existsByEmail(String email);

    Optional<Account> findByEmail(String email);

    @Query("SELECT a FROM Account a JOIN a.user u JOIN a.role r WHERE a.user.username = :username")
    Optional<Account> findByUsername(@Param("username") String username);
}

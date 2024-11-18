package com.global.project.services.impl;

import com.global.project.configuration.AccountDetailsImpl;
import com.global.project.entity.Account;
import com.global.project.entity.Role;
import com.global.project.repository.AccountRepository;
import com.global.project.repository.RoleRepository;
import com.global.project.services.IAccountService;
import com.global.project.utils.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements IAccountService, UserDetailsService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public AccountService(RoleRepository roleRepository, PasswordEncoder passwordEncoder, AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        Account checkExsit = accountRepository.findByUsername("admin").orElse(null);
        if (checkExsit == null) {
            Account account = new Account();
            account.setEmail("admin");
            account.setUsername("admin");
            account.setIsActive(true);
            account.setEmail("test@gmail.com");

            Role role = roleRepository.findByName(Const.ROLE_ADMIN);
            if (role == null) {
                role = new Role();
                role.setName(Const.ROLE_ADMIN);
                roleRepository.saveAndFlush(role);
            }
            account.setRole(role);
            account.setPassword(passwordEncoder.encode("admin"));
            accountRepository.save(account);
        }
        Role roleAdmin = roleRepository.findByName(Const.ROLE_ADMIN);
        if (roleAdmin == null) {
            roleAdmin = new Role();
            roleAdmin.setName(Const.ROLE_ADMIN);
            roleRepository.saveAndFlush(roleAdmin);
        }
        Role roleUser = roleRepository.findByName(Const.ROLE_USER);
        if (roleUser == null) {
            roleUser = new Role();
            roleUser.setName(Const.ROLE_USER);
            roleRepository.saveAndFlush(roleUser);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new AccountDetailsImpl(accountRepository.findByUsername(username).get());
    }
}

package com.ecommerce.bookstore.service;

import com.ecommerce.bookstore.model.Account;
import com.ecommerce.bookstore.repository.AccountRepository;
import com.ecommerce.bookstore.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public void saveAccount(Account account) {
        account.setPasswordHash(passwordEncoder.encode(account.getPasswordHash()));
        accountRepository.save(account);
    }

    public String generateToken(Authentication authentication) {
        return jwtTokenProvider.createToken(authentication);
    }
}
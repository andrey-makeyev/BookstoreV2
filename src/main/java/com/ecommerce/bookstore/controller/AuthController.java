package com.ecommerce.bookstore.controller;

import com.ecommerce.bookstore.dto.LoginRequest;
import com.ecommerce.bookstore.dto.LoginResponse;
import com.ecommerce.bookstore.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            String token = accountService.generateToken(loginRequest.getUserName(), loginRequest.getPassword());
            return ResponseEntity.ok(new LoginResponse(token));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body(new LoginResponse(null));
        }
    }
}
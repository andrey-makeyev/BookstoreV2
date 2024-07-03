package com.ecommerce.bookstore.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class LoginRequest {
    private String userName;
    private String password;
}
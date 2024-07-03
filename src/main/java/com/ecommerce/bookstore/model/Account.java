package com.ecommerce.bookstore.model;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.*;

import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name = "account")
@Data
public class Account implements Serializable {

    private static final long serialVersionUID = -2054386655979281969L;

    public static final String ADMIN = "admin";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false, unique = true)
    private String userName;

    @Column(length = 128, nullable = false)
    private String passwordHash;

    @Column(length = 1, nullable = false)
    private boolean isActive;

    @Column(length = 20, nullable = false)
    private String userRole;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Book> books;

    public void setPasswordHash(String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.passwordHash = passwordEncoder.encode(password);
    }

    @Override
    public String toString() {
        return "[" + this.userName + "," + this.passwordHash + "," + this.userRole + "]";
    }
}
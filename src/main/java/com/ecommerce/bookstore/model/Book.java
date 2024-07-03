package com.ecommerce.bookstore.model;

import lombok.Data;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;

@Data
@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String isbn;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String publisher;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private double price;

    @JdbcTypeCode(Types.VARBINARY)
    @Column(name = "image", columnDefinition = "bytea")
    private byte[] image;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
package com.ecommerce.bookstore.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "books")
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "isbn", length = 20, nullable = false)
    private String isbn;

    @Column(name = "title", length = 128, nullable = false)
    private String title;

    @Column(name = "author", length = 50, nullable = false)
    private String author;

    @Column(name = "publisher", length = 128, nullable = false)
    private String publisher;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "description", length = 255, nullable = false)
    private String description;

    @Column(name = "price", nullable = false)
    private double price;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB", name = "image", nullable = true)
    private String image;

    // Getters and setters...
}
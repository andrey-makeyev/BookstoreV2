package com.ecommerce.bookstore.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(callSuper = false)
public class BookDTO extends RepresentationModel<BookDTO> {

    private Long id;
    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private Integer year;
    private String description;
    private double price;
    private String imageUrl;


}
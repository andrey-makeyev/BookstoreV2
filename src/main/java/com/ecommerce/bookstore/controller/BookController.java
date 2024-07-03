package com.ecommerce.bookstore.controller;

import com.ecommerce.bookstore.model.Book;
import com.ecommerce.bookstore.dto.BookDTO;
import com.ecommerce.bookstore.repository.BookRepository;
import com.ecommerce.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api")
public class BookController {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;

    @GetMapping("/image/{id}")
    public ResponseEntity<ByteArrayResource> getImage(@PathVariable Long id) {
        logger.debug("Fetching image for book ID: {}", id);
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isPresent() && bookOptional.get().getImage() != null) {
            byte[] image = bookOptional.get().getImage();
            ByteArrayResource resource = new ByteArrayResource(image);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + id + ".png\"")
                    .contentType(MediaType.IMAGE_PNG)
                    .contentLength(image.length)
                    .body(resource);
        } else {
            logger.warn("Image not found for book ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/books")
    public List<BookDTO> getAllBooks(
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "50") int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Book> page = bookRepository.findAll(pageable);
        List<Book> books = page.getContent();
        return books.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<EntityModel<BookDTO>> getBookDetails(@PathVariable Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            BookDTO bookDTO = convertToDTO(book);

            bookDTO.add(WebMvcLinkBuilder.linkTo(
                            WebMvcLinkBuilder.methodOn(BookController.class)
                                    .getAllBooks(0, 50))
                    .withRel("all-books"));

            EntityModel<BookDTO> entityModel = EntityModel.of(bookDTO);
            return ResponseEntity.ok(entityModel);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("admin/books")
    public BookDTO createBook(@RequestBody BookDTO bookDTO) {
        Book book = convertToEntity(bookDTO);
        Book savedBook = bookRepository.save(book);
        return convertToDTO(savedBook);
    }

    @PutMapping("admin/books/{id}")
    public BookDTO updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            book.setIsbn(bookDTO.getIsbn());
            book.setTitle(bookDTO.getTitle());
            book.setAuthor(bookDTO.getAuthor());
            book.setPublisher(bookDTO.getPublisher());
            book.setYear(bookDTO.getYear());
            book.setDescription(bookDTO.getDescription());
            book.setPrice(bookDTO.getPrice());
            if (bookDTO.getImageUrl() != null) {
                // Пример как можно конвертировать URL обратно в байты, если это потребуется
                // byte[] imageBytes = downloadImage(bookDTO.getImageUrl());
                // book.setImage(imageBytes);
            }
            Book updatedBook = bookRepository.save(book);
            return convertToDTO(updatedBook);
        } else {
            return null;
        }
    }

    @DeleteMapping("admin/books/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookRepository.deleteById(id);
    }

    private BookDTO convertToDTO(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setIsbn(book.getIsbn());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setAuthor(book.getAuthor());
        bookDTO.setPublisher(book.getPublisher());
        bookDTO.setYear(book.getYear());
        bookDTO.setDescription(book.getDescription());
        bookDTO.setPrice(book.getPrice());

        if (book.getImage() != null) {
            String imageUrl = "http://localhost:8080/api/image/" + book.getId();
            bookDTO.setImageUrl(imageUrl);
        }

        bookDTO.add(WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(BookController.class)
                                .getAllBooks(0, 50))
                .withRel("all-books"));

        return bookDTO;
    }

    private Book convertToEntity(BookDTO bookDTO) {
        Book book = new Book();
        book.setId(bookDTO.getId());
        book.setIsbn(bookDTO.getIsbn());
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setPublisher(bookDTO.getPublisher());
        book.setYear(bookDTO.getYear());
        book.setDescription(bookDTO.getDescription());
        book.setPrice(bookDTO.getPrice());
        return book;
    }
}
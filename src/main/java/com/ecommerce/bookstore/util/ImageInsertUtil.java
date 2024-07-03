package com.ecommerce.bookstore.util;

import com.ecommerce.bookstore.model.Book;
import com.ecommerce.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Optional;

@Component
@EnableScheduling
public class ImageInsertUtil {

    private static final Logger logger = LoggerFactory.getLogger(ImageInsertUtil.class);

    @Value("${image.base-path}")
    private String basePath;

    private final BookRepository bookRepository;
    private final DataSource dataSource;

    public ImageInsertUtil(BookRepository bookRepository, DataSource dataSource) {
        this.bookRepository = bookRepository;
        this.dataSource = dataSource;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void onApplicationEvent() {
        insertImages();
    }

    @Scheduled(fixedRate = 60000) // Check every minute
    public void scheduleImageInsertion() {
        logger.info("Scheduled task to insert images...");
        insertImages();
    }

    public void insertImages() {
        for (int i = 1; i <= 75; i++) {
            String imagePath = basePath + "/generated_image (" + i + ").png";
            logger.debug("Inserting image: {}", imagePath);
            try {
                byte[] imageData = readImageFromFile(imagePath);
                updateBookImage(i, imageData);
                logger.info("Successfully inserted image {}", i);
            } catch (IOException | SQLException e) {
                logger.error("Failed to insert image {}: {}", i, e.getMessage(), e);
            }
        }
    }

    private byte[] readImageFromFile(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            logger.error("File not found: {}", path);
            throw new IOException("File not found: " + path);
        }

        try (FileInputStream fis = new FileInputStream(file)) {
            return fis.readAllBytes();
        }
    }

    private void updateBookImage(int id, byte[] imageData) throws SQLException {
        Optional<Book> bookOptional = bookRepository.findById((long) id);
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            if (book.getImage() == null) {
                book.setImage(imageData);
                bookRepository.save(book);
                logger.debug("Updated book ID {} with image data", id);
            } else {
                logger.info("Book ID {} already has an image, skipping update.", id);
            }
        } else {
            logger.warn("Book with id {} not found", id);
        }
    }
}
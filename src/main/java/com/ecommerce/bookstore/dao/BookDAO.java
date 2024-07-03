/*package com.ecommerce.bookstore.dao;

import com.ecommerce.bookstore.model.Book;
import com.ecommerce.bookstore.pagination.Pagination;
import com.ecommerce.bookstore.repository.BookRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import jakarta.persistence.NoResultException;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@Transactional
@Repository
public class BookDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private BookRepository bookRepository;

    public Book findBook(String isbn) {
        try {
            String findIsbnQuery = "SELECT b FROM " + Book.class.getName() + " b WHERE b.isbn = :isbn ";

            Session session = this.sessionFactory.getCurrentSession();
            Query<Book> query = session.createQuery(findIsbnQuery, Book.class);
            query.setParameter("isbn", isbn);
            return query.uniqueResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void saveBook(Book book) throws IOException {
        Session session = this.sessionFactory.getCurrentSession();
        session.saveOrUpdate(book);
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book was not found for id " + id));
    }

    public void deleteBookById(Long id) {
        this.bookRepository.deleteById(id);
    }

    public Pagination<Book> queryBooks(int page, int maxResult, int maxNavigationPage, String likeName) {
        String sql = "SELECT b FROM Book b";
        if (likeName != null && !likeName.isEmpty()) {
            sql += " WHERE LOWER(b.name) LIKE :likeName";
        }
        sql += " ORDER BY b.createDate DESC";

        Session session = this.sessionFactory.getCurrentSession();
        Query<Book> query = session.createQuery(sql, Book.class);

        if (likeName != null && !likeName.isEmpty()) {
            query.setParameter("likeName", "%" + likeName.toLowerCase() + "%");
        }

        return new Pagination<>(query, page, maxResult, maxNavigationPage);
    }

    public Pagination<Book> queryBooks(int page, int maxResult, int maxNavigationPage) {
        return queryBooks(page, maxResult, maxNavigationPage, null);
    }
}*/
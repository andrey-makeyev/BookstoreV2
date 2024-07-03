/*package com.ecommerce.bookstore.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.ecommerce.bookstore.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class AccountDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public Account findAccount(String userName) {
        Session session = this.sessionFactory.getCurrentSession();
        return session.createQuery("select a from Account a where a.userName = :userName", Account.class)
                .setParameter("userName", userName)
                .getSingleResult();
    }
}*/
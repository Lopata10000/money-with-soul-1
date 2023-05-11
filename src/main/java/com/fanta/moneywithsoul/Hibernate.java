package com.fanta.moneywithsoul;

import com.fanta.dao.ExchangeRateDAO;
import com.fanta.dao.TransactionDAO;
import com.fanta.entity.ExchangeRate;
import com.fanta.entity.Transaction;
import com.fanta.entity.User;
import com.fanta.service.TransactionService;
import com.fanta.service.UserService;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class Hibernate {
    public static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void main(String[] args) {
        UserService userService = new UserService();
        User user = userService.getById(1L);
        System.out.println("User ID: " + user.getUserId());
            System.out.println("First Name: " + user.getFirstName());
            System.out.println("Last Name: " + user.getLastName());
            System.out.println("Email: " + user.getEmail());
            System.out.println("Registered At: " + user.getRegisteredAt());
            System.out.println("User Status: " + user.getUserStatus());
            System.out.println("-------------------------------------");
        ExchangeRateDAO exchangeRateDAO = new ExchangeRateDAO();
        ExchangeRate exchangeRate = exchangeRateDAO.findById(1L);
        TransactionService transactionService = new TransactionService();
        Transaction transaction =  transactionService.createTransaction(1L, "Costss", Timestamp.valueOf(LocalDateTime.now()), BigDecimal.valueOf(20), "Ysyyyss", 1L);
        transactionService.save(transaction);
//        TransactionService transactionService = new TransactionService();
//        Transaction transaction = transactionService.createTransaction(1L, "Витрати", Timestamp.valueOf(LocalDateTime.now()), BigDecimal.valueOf(20), "Витрати на дім", exchangeRate, "USD");
//        transactionService.save(transaction);
//        List<User> users = userService.getAll();
//        for (User user : users) {
//            System.out.println("User ID: " + user.getUserId());
//            System.out.println("First Name: " + user.getFirstName());
//            System.out.println("Last Name: " + user.getLastName());
//            System.out.println("Email: " + user.getEmail());
//            System.out.println("Registered At: " + user.getRegisteredAt());
//            System.out.println("User Status: " + user.getUserStatus());
//            System.out.println("-------------------------------------");
//        }


    }
}

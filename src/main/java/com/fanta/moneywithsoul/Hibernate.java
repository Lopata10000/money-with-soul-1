package com.fanta.moneywithsoul;

import static java.time.LocalTime.now;

import com.fanta.dao.UserDAO;
import com.fanta.entity.User;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

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
            UserDAO userDAO = new UserDAO();

            User user = new User();
            user.setFirstName("Іван2");
            user.setLastName("Іванов");
            user.setEmail("ivan4.ivanov@example.com");
            user.setPasswordHash("qwerty");
            user.setRegisteredAt(Timestamp.valueOf(LocalDateTime.now()));
            user.setUserStatus("active");
        userDAO.update(user);
    }
}

package com.fanta.moneywithsoul.database;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


/**
 * The type Hibernate.
 */
public class Hibernate {

    /**
     * The constant sessionFactory.
     */
    public static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
}

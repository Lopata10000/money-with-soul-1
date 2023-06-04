package com.fanta.moneywithsoul.database;

import com.fanta.moneywithsoul.entity.Cost;
import com.fanta.moneywithsoul.service.CostService;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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

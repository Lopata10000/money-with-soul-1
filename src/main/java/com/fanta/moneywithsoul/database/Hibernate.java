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

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        CostService costService = new CostService();
        Cost cost =
                costService.saveCost(
                        2L,
                        3L,
                        2L,
                        2L,
                        Timestamp.valueOf(LocalDateTime.now()),
                        BigDecimal.valueOf(12),
                        "Fanta");
        costService.save(cost);
    }
}

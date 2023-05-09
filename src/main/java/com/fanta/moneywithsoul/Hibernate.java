package com.fanta.moneywithsoul;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Hibernate {
    private static final SessionFactory sessionFactory = buildSessionFactory();

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

    Session session = Hibernate.getSessionFactory().openSession();
    Transaction transaction = null;
    //
    // try {
    //        transaction = session.beginTransaction();
    //
    //        User user = new User();
    //        user.setFirstName("John");
    //        user.setLastName("Doe");
    //        user.setEmail("johndoe@example.com");
    //        user.setPasswordHash("myPasswordHash");
    //        user.setRegisteredAt(LocalDateTime.now());
    //        user.setUserStatus("active");
    //
    //        session.save(user);
    //
    //        transaction.commit();
    //    } catch (Exception e) {
    //        if (transaction != null) {
    //            transaction.rollback();
    //        }
    //        e.printStackTrace();
    //    } finally {
    //        session.close();
    //    }

}

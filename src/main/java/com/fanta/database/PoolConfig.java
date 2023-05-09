package com.fanta.database;

import com.fanta.entity.User;
import com.fanta.moneywithsoul.Hibernate;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.sql.DataSource;

public class PoolConfig implements DataBaseConfig {
    private static HikariDataSource dataSource;

    static {
        HikariConfig pullConfiguration = new HikariConfig();
        pullConfiguration.setJdbcUrl(url);
        pullConfiguration.setUsername(user);
        pullConfiguration.setPassword(password);
        pullConfiguration.setMaximumPoolSize(10);
        pullConfiguration.setConnectionTimeout(5000);
        pullConfiguration.setMaxLifetime(1800000);
        pullConfiguration.setPoolName("money-with-soul Pool");
        dataSource = new HikariDataSource(pullConfiguration);
    }


    public void Test() {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            Session session = Hibernate.getSessionFactory().openSession();
            Transaction transaction = null;
             try {
                 LocalDateTime localDateTime = LocalDateTime.now();
                 Timestamp timestamp = Timestamp.valueOf(localDateTime);
                    transaction = session.beginTransaction();
                    User user = new User();
                    user.setFirstName("John");
                    user.setLastName("Doe");
                    user.setEmail("johndoe@example.com");
                    user.setPasswordHash("myPasswordHash");
                    user.setUserStatus("active");
                 user.setRegisteredAt(timestamp);
                 session.save(user);

                 transaction.commit();
                } catch (Exception e) {
                    if (transaction != null) {
                        transaction.rollback();
                    }
                    e.printStackTrace();
                } finally {
                    session.close();
                }
        } catch (SQLException e) {
            System.err.println("User creation failed: " + e.getMessage());
        }
    }

    public void closeDataSource(DataSource dataSource) {
        if (dataSource instanceof HikariDataSource) {
            ((HikariDataSource) dataSource).close();
        }
    }
}

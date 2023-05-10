package com.fanta.dao;

import static com.fanta.moneywithsoul.Hibernate.sessionFactory;

import com.fanta.database.DataBaseConfig;
import com.fanta.entity.User;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class BaseDAO<T> implements DataBaseConfig {
    protected final DataSource dataSource;

    public BaseDAO() {
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

    protected void executeWithTransaction(Runnable runnable) {
        try (Connection connection = dataSource.getConnection();
             Session session = sessionFactory.withOptions().connection(connection).openSession()) {
            Transaction transaction = session.beginTransaction();
            runnable.run();
            transaction.commit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}

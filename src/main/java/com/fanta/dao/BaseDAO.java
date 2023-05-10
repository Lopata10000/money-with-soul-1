package com.fanta.dao;

import static com.fanta.moneywithsoul.Hibernate.sessionFactory;

import com.fanta.database.DataBaseConfig;
import com.fanta.database.PoolConfig;
import java.sql.Connection;
import org.hibernate.Session;
import org.hibernate.Transaction;

public abstract class BaseDAO<T> extends PoolConfig implements DataBaseConfig {

    protected void executeWithTransaction(Runnable runnable) {
        try (Connection connection = dataSource.getConnection();
                Session session =
                        sessionFactory.withOptions().connection(connection).openSession()) {
            Transaction transaction = session.beginTransaction();
            runnable.run();
            transaction.commit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}

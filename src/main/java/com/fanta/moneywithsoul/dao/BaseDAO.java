package com.fanta.moneywithsoul.dao;

import com.fanta.moneywithsoul.database.DataBaseConfig;
import com.fanta.moneywithsoul.database.Hibernate;
import com.fanta.moneywithsoul.database.PoolConfig;
import java.sql.Connection;
import org.hibernate.Session;
import org.hibernate.Transaction;


/**
 * The type Base dao.
 *
 * @param <T> the type parameter
 */
public abstract class BaseDAO<T> extends PoolConfig implements DataBaseConfig {


    /**
     * Execute with transaction.
     *
     * @param runnable the runnable
     */
    protected void executeWithTransaction(Runnable runnable) {
        try (Connection connection = dataSource.getConnection();
                Session session =
                        Hibernate.sessionFactory
                                .withOptions()
                                .connection(connection)
                                .openSession()) {
            Transaction transaction = session.beginTransaction();
            runnable.run();
            transaction.commit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}

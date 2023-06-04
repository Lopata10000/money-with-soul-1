package com.fanta.moneywithsoul.database;

import static com.fanta.moneywithsoul.database.PoolConfig.dataSource;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.Connection;

/**
 * The interface Data base config.
 */
public interface DataBaseConfig {
    /**
     * The constant url.
     */
    String url = "jdbc:postgresql://containers-us-west-35.railway.app:7368/railway";
    /**
     * The constant user.
     */
    String user = "postgres";
    /**
     * The constant password.
     */
    String password = "ntwuzzqSGxGI1fqMwgnX";
}

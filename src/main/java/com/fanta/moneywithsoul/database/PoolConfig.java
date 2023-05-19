package com.fanta.moneywithsoul.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;

public class PoolConfig implements DataBaseConfig {
    public static final HikariDataSource dataSource;

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

    public void closeDataSource(DataSource dataSource) {
        if (dataSource instanceof HikariDataSource) {
            ((HikariDataSource) dataSource).close();
        }
    }
}

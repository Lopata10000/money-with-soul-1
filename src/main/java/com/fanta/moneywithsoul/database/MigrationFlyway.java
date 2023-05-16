package com.fanta.moneywithsoul.database;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MigrationFlyway implements DataBaseConfig {
    private static final Logger logger = LoggerFactory.getLogger(MigrationFlyway.class);

    public static void main(String[] args) {
        Flyway flyway =
                Flyway.configure().dataSource(url, user, password).locations("db/migration").load();
        flyway.baseline();
        flyway.migrate();
    }
}

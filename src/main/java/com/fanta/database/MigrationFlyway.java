package com.fanta.database;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MigrationFlyway implements DataBaseConfig {
    private static final Logger logger = LoggerFactory.getLogger(MigrationFlyway.class);

    public static void migrationWihFlyway() {

        Flyway flyway =
                Flyway.configure().dataSource(url, user, password).locations("db/migration").load();
        flyway.migrate();
    }
}

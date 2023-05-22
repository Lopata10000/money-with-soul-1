package com.fanta.moneywithsoul.database;

import org.flywaydb.core.Flyway;

/**
 * The type Migration flyway.
 */
public class MigrationFlyway implements DataBaseConfig {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        Flyway flyway =
                Flyway.configure().dataSource(url, user, password).locations("db/migration").load();
        flyway.baseline();
        flyway.migrate();
    }
}

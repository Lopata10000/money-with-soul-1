package com.fanta.moneywithsoul.database;

import org.flywaydb.core.Flyway;

public class MigrationFlyway implements DataBaseConfig {

    public static void main(String[] args) {
        Flyway flyway =
                Flyway.configure().dataSource(url, user, password).locations("db/migration").load();
        flyway.baseline();
        flyway.migrate();
    }
}

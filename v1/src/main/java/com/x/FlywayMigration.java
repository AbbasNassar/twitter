package com.x;

import org.flywaydb.core.Flyway;

public class FlywayMigration {
    public static void migrateDatabase(String url, String user, String password) {
        Flyway flyway = Flyway.configure()
                .dataSource(url, user, password)
                .locations("db/migration/")
                .baselineOnMigrate(true)
                .load();
        flyway.migrate();
    }
}

package com.itrex.kaliaha.service;

import org.flywaydb.core.Flyway;

import static com.itrex.kaliaha.property.Properties.H2_URL;
import static com.itrex.kaliaha.property.Properties.H2_USER;
import static com.itrex.kaliaha.property.Properties.H2_PASSWORD;
import static com.itrex.kaliaha.property.Properties.MIGRATIONS_LOCATION;

public class FlywayService {
    private Flyway flyway;

    public FlywayService() {
        init();
    }

    public void migrate() {
        flyway.migrate();
    }

    public void clean() {
        flyway.clean();
    }

    private void init() {
        flyway = Flyway.configure()
                .dataSource(H2_URL, H2_USER, H2_PASSWORD)
                .locations(MIGRATIONS_LOCATION)
                .load();
    }
}
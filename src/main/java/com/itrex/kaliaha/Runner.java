package com.itrex.kaliaha;

import com.itrex.kaliaha.service.FlywayService;
import org.h2.jdbcx.JdbcConnectionPool;

import static com.itrex.kaliaha.property.Properties.*;

public class Runner {
    public static void main(String[] args) {
        FlywayService flywayService = new FlywayService();
        flywayService.migrate();

        JdbcConnectionPool jdbcConnectionPool = JdbcConnectionPool.create(H2_URL, H2_USER, H2_PASSWORD);

        jdbcConnectionPool.dispose();

    }
}
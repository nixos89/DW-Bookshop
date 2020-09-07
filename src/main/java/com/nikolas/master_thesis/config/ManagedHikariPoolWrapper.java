package com.nikolas.master_thesis.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.dropwizard.db.ManagedDataSource;

public class ManagedHikariPoolWrapper extends HikariDataSource implements ManagedDataSource {

    public ManagedHikariPoolWrapper(HikariConfig hikariConfig) {
        super(hikariConfig);
    }

    @Override
    public void start() throws Exception {
    }

    @Override
    public void stop() throws Exception {
        this.close();
    }
}

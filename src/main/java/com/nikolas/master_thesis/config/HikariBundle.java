package com.nikolas.master_thesis.config;

import com.nikolas.master_thesis.DropwizardMasterThesisConfiguration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;


/**
 * Dropwizard bundle to add Hikari Connection Pool support to Jersey resources.
 *
 * Note that this bundle should be registered before any other bundles that depend on it.
 */
public class HikariBundle implements ConfiguredBundle<HikariConfigurationProvider> {

    @Override
    public void run(HikariConfigurationProvider hikariConfigurationProvider, Environment environment) throws Exception {
        // The Data Source is created by this call, and is retained as a field on the configuration object itself.
        // This is why this bundle has to be registered before any other bundles that might call
        hikariConfigurationProvider.getHikariConfiguration().buildDataSource(environment.metrics(), environment.healthChecks(), "database");
        environment.lifecycle().manage(hikariConfigurationProvider.getHikariConfiguration().getManagedDataSource());
    }

    @Override
    public void initialize(Bootstrap<?> bootstrap) {

    }
}

package com.nikolas.master_thesis;

import com.nikolas.master_thesis.health.TemplateHealthCheck;
import com.nikolas.master_thesis.mapstruct_mappers.AuthorMSMapper;
import com.nikolas.master_thesis.mapstruct_mappers.BookMSMapper;
import com.nikolas.master_thesis.mapstruct_mappers.CategoryMSMapper;
import com.nikolas.master_thesis.resources.*;
import com.nikolas.master_thesis.service.*;
import com.nikolas.master_thesis.util.DWBExceptionMapper;
import io.dropwizard.Application;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.jersey.jackson.JsonProcessingExceptionMapper;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.postgres.PostgresPlugin;

import java.nio.file.attribute.UserPrincipalLookupService;

public class DropwizardMasterThesisApplication extends Application<DropwizardMasterThesisConfiguration> {

    public static void main(final String[] args) throws Exception {
        new DropwizardMasterThesisApplication().run(args);
    }

    @Override
    public String getName() {
        return "DropwizardMasterThesis";
    }


    @Override
    public void initialize(final Bootstrap<DropwizardMasterThesisConfiguration> bootstrap) {
        // TODO: make use of  "DropwizardMasterThesisResourceConfig" class for Dependency Injection
        DropwizardMasterThesisResourceConfig resourceConfig = new DropwizardMasterThesisResourceConfig();
//        bootstrap.

    }


    @Override
    public void run(final DropwizardMasterThesisConfiguration configuration, final Environment environment) {
        final JdbiFactory jdbiFactory = new JdbiFactory();
        final Jdbi jdbi = jdbiFactory.build(environment, configuration.getDataSourceFactory(), "postgresql");
        // next line of code is implemented by this source: http://jdbi.org/#_postgresql
        // ...so it can recognize DB vendor to process arrays from JSON
        // more info @: https://github.com/jdbi/jdbi/issues/992
        jdbi.installPlugin(new PostgresPlugin());

        final TemplateHealthCheck healthCheck = new TemplateHealthCheck(configuration.getTemplate());
        environment.healthChecks().register("template", healthCheck);
        environment.jersey().register(new DWBExceptionMapper());
        environment.jersey().register(new JsonProcessingExceptionMapper(true));
        environment.jersey().register(BookResource.class);
        environment.jersey().register(CategoryResource.class);
        environment.jersey().register(AuthorResource.class);
        environment.jersey().register(OrderResource.class);
    }

}

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
        // TODO: application initialization

    }


    @Override
    public void run(final DropwizardMasterThesisConfiguration configuration, final Environment environment) {
        final JdbiFactory jdbiFactory = new JdbiFactory();
        final Jdbi jdbi = jdbiFactory.build(environment, configuration.getDataSourceFactory(), "postgresql");
        /* Next line of code is implemented by this source: http://jdbi.org/#_postgresql so it can recognize DB vendor to process arrays from JSON
         * More info @: https://github.com/jdbi/jdbi/issues/992 */
        jdbi.installPlugin(new PostgresPlugin());

        final BookMSMapper bookMSMapper = BookMSMapper.INSTANCE;

        final BookService bookService = new BookService(jdbi, bookMSMapper);
        final CategoryService categoryService = new CategoryService(jdbi);
        final AuthorService authorService = new AuthorService(jdbi);
        final OrderService orderService = new OrderService(jdbi, bookMSMapper);

        final BookResource bookResource = new BookResource(bookService);
        final AuthorResource authorResource = new AuthorResource(authorService);
        final CategoryResource categoryResource = new CategoryResource(categoryService);
        final OrderResource orderResource = new OrderResource(orderService);

        final TemplateHealthCheck healthCheck = new TemplateHealthCheck(configuration.getTemplate());
        environment.healthChecks().register("template", healthCheck);
        environment.jersey().register(new DWBExceptionMapper());
        environment.jersey().register(new JsonProcessingExceptionMapper(true));
        environment.jersey().register(bookResource);
        environment.jersey().register(categoryResource);
        environment.jersey().register(authorResource);
        environment.jersey().register(orderResource);
    }

}

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
    } // initialize


    @Override
    public void run(final DropwizardMasterThesisConfiguration configuration, final Environment environment) {
        final JdbiFactory jdbiFactory = new JdbiFactory();
        final Jdbi jdbi = jdbiFactory.build(environment, configuration.getDataSourceFactory(), "postgresql");
        // next line of code is implemented by this source: http://jdbi.org/#_postgresql
        // ...so it can recognize DB vendor to process arrays from JSON
        // more info @: https://github.com/jdbi/jdbi/issues/992
        jdbi.installPlugin(new PostgresPlugin());

        final AuthorMSMapper authorMSMapper = AuthorMSMapper.INSTANCE;
        final BookMSMapper bookMSMapper = BookMSMapper.INSTANCE;
        final CategoryMSMapper categoryMSMapper = CategoryMSMapper.INSTANCE;

        // NOTE: instantiated for needs of AuthorResource class constructor
        final BookService bookService = new BookService(jdbi, bookMSMapper);
        final CategoryService categoryService = new CategoryService(jdbi);
        final AuthorService authorService = new AuthorService(jdbi);
        final UserService userService = new UserService(jdbi);
        final OrderService orderService = new OrderService(jdbi);

        // declaring RESOURCE variables with final modifier because it's creating them as Singletons
        final AccountResource accountResource = new AccountResource(jdbi);
        final UserAResource userAResource = new UserAResource(jdbi);
        final BookResource bookResource = new BookResource(bookService);
        final AuthorResource authorResource = new AuthorResource(authorService); // changed constructor param
        final CategoryResource categoryResource = new CategoryResource(categoryService);
        final UserResource userResource = new UserResource(userService);
        final OrderResource orderResource = new OrderResource(orderService);

        final TemplateHealthCheck healthCheck = new TemplateHealthCheck(configuration.getTemplate());
        environment.healthChecks().register("template", healthCheck);
        environment.jersey().register(new DWBExceptionMapper());

        environment.jersey().register(accountResource);
        environment.jersey().register(userAResource);
        environment.jersey().register(new JsonProcessingExceptionMapper(true));
        environment.jersey().register(bookResource);
        environment.jersey().register(categoryResource);
        environment.jersey().register(authorResource);
        environment.jersey().register(userResource);
        environment.jersey().register(orderResource);
    }// run(..)

}

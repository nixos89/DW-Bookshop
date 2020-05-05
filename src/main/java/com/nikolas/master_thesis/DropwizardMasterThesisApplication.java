package com.nikolas.master_thesis;

import com.nikolas.master_thesis.db.*;
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
import org.jdbi.v3.core.Handle;
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
    public void initialize(final Bootstrap<DropwizardMasterThesisConfiguration> bootstrap) { }

    @Override
    public void run(final DropwizardMasterThesisConfiguration configuration, final Environment environment) {
        final JdbiFactory jdbiFactory = new JdbiFactory();
        final Jdbi jdbi = jdbiFactory.build(environment, configuration.getDataSourceFactory(), "postgresql");
        /* Next line of code is implemented by this source: http://jdbi.org/#_postgresql so it can recognize DB vendor to process arrays from JSON
         * More info @: https://github.com/jdbi/jdbi/issues/992 */
        jdbi.installPlugin(new PostgresPlugin());

        // creating tables IFF needed
        createTables(jdbi);

        final BookMSMapper bookMSMapper = BookMSMapper.INSTANCE;

        final BookService bookService = new BookService(jdbi, bookMSMapper);
        final CategoryService categoryService = new CategoryService(jdbi);
        final AuthorService authorService = new AuthorService(jdbi);
        final OrderService orderService = new OrderService(jdbi, bookMSMapper);
        final UserService userService = new UserService(jdbi);

        final BookResource bookResource = new BookResource(bookService);
        final AuthorResource authorResource = new AuthorResource(authorService);
        final CategoryResource categoryResource = new CategoryResource(categoryService);
        final OrderResource orderResource = new OrderResource(orderService, userService);

        final TemplateHealthCheck healthCheck = new TemplateHealthCheck(configuration.getTemplate());
        environment.healthChecks().register("template", healthCheck);
        environment.jersey().register(new DWBExceptionMapper());
        environment.jersey().register(new JsonProcessingExceptionMapper(true));
        environment.jersey().register(bookResource);
        environment.jersey().register(categoryResource);
        environment.jersey().register(authorResource);
        environment.jersey().register(orderResource);
    }


    void createTables(Jdbi jdbi){
        Handle handle = jdbi.open();
        AuthorDAO authorDAO = handle.attach(AuthorDAO.class);
        BookDAO bookDAO = handle.attach(BookDAO.class);
        CategoryDAO categoryDAO = handle.attach(CategoryDAO.class);
        OrderDAO orderDAO = handle.attach(OrderDAO.class);
        OrderItemDAO orderItemDAO = handle.attach(OrderItemDAO.class);
        RoleDAO roleDAO = handle.attach(RoleDAO.class);
        UserDAO userDAO = handle.attach(UserDAO.class);

        authorDAO.createTableAuthor();
        authorDAO.createTableAuthorBook();
        bookDAO.createBookTable();
        categoryDAO.createCategoryTable();
        categoryDAO.createTableBookCategory();
        orderDAO.createOrderTable();
        orderItemDAO.createOrderItemTable();
        roleDAO.createTableRole();
        userDAO.createUserTable();

        handle.close();
    }

}

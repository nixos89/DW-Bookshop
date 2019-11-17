package com.nikolas.master_thesis;

import com.nikolas.master_thesis.health.TemplateHealthCheck;
import com.nikolas.master_thesis.resources.*;
import io.dropwizard.Application;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.jersey.jackson.JsonProcessingExceptionMapper;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.jdbi.v3.core.Jdbi;

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
    public void run(final DropwizardMasterThesisConfiguration configuration,
                    final Environment environment) {
        final JdbiFactory jdbiFactory = new JdbiFactory();
        final Jdbi jdbi = jdbiFactory.build(environment, configuration.getDataSourceFactory(), "postgresql");

//        final DWMasterThesisResource resource = new DWMasterThesisResource(
//                configuration.getTemplate(), configuration.getDefaultName());
//        final PersonResource personResource = new PersonResource(jdbi);
        final AccountResource accountResource = new AccountResource(jdbi);
        final UserAResource userAResource = new UserAResource(jdbi);
        final BookResource bookResource = new BookResource(jdbi);
        final AuthorResource authorResource = new AuthorResource(jdbi);
        final CategoryResource categoryResource = new CategoryResource(jdbi);
        final UserResource userResource = new UserResource(jdbi);
        final OrderResource orderResource = new OrderResource(jdbi);



        final TemplateHealthCheck healthCheck = new TemplateHealthCheck(configuration.getTemplate());
        environment.healthChecks().register("template", healthCheck);
//        environment.jersey().register(personResource);
//        environment.jersey().register(resource);
        environment.jersey().register(accountResource);
        environment.jersey().register(userAResource);
        environment.jersey().register(new JsonProcessingExceptionMapper(true));
        environment.jersey().register(bookResource);
        environment.jersey().register(categoryResource);
        environment.jersey().register(authorResource);
        environment.jersey().register(userResource);
        environment.jersey().register(orderResource);

        // Debugging::START
//        HandleJdbiExamples.perform(jdbi);
        // Debugging::END

    }// run(..)

}

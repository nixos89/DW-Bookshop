package com.nikolas.master_thesis;

import com.nikolas.master_thesis.resources.AuthorResource;
import com.nikolas.master_thesis.resources.BookResource;
import com.nikolas.master_thesis.resources.CategoryResource;
import com.nikolas.master_thesis.resources.OrderResource;
import com.nikolas.master_thesis.service.AuthorService;
import com.nikolas.master_thesis.service.BookService;
import com.nikolas.master_thesis.service.CategoryService;
import com.nikolas.master_thesis.service.OrderService;
import com.nikolas.master_thesis.service.impl.AuthorServiceImpl;
import com.nikolas.master_thesis.service.impl.BookServiceImpl;
import com.nikolas.master_thesis.service.impl.CategoryServiceImpl;
import com.nikolas.master_thesis.service.impl.OrderServiceImpl;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

public class DropwizardMasterThesisResourceConfig extends ResourceConfig {

    public DropwizardMasterThesisResourceConfig() {
        register(AuthorResource.class);
        register(BookResource.class);
        register(CategoryResource.class);
        register(OrderResource.class);
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(new AuthorServiceImpl()).to(AuthorService.class);
                bind(new BookServiceImpl()).to(BookService.class);
                bind(new CategoryServiceImpl()).to(CategoryService.class);
                bind(new OrderServiceImpl()).to(OrderService.class);
            }
        });
    }
}

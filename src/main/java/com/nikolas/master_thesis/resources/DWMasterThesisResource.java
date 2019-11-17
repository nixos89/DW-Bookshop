package com.nikolas.master_thesis.resources;

import com.codahale.metrics.annotation.Timed;
import com.nikolas.master_thesis.api.Saying;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Path("/hello-world")
@Produces(MediaType.APPLICATION_JSON)
public class DWMasterThesisResource {

    private final String template;
    private final String defaultName;
    private final AtomicLong counter;

    public DWMasterThesisResource(String template, String defaultName) {
        this.template = template;
        this.defaultName = defaultName;
        this.counter = new AtomicLong();
    }

    @GET
    @Timed
    public Saying sayHello(@QueryParam("name") Optional<String> name) {
        final String value = String.format(template, name.orElse(defaultName)); // must be IMMUTABLE!
        return new Saying(counter.getAndIncrement(), value);
    }
}

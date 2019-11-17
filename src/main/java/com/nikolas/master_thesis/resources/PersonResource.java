package com.nikolas.master_thesis.resources;

import com.nikolas.master_thesis.api.PersonDTO;
import com.nikolas.master_thesis.db.PersonDAO;
import org.jdbi.v3.core.Jdbi;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Path("/persons")
@Produces(MediaType.APPLICATION_JSON)
public class PersonResource {

    private final PersonDAO personDAO;

    public PersonResource(Jdbi jdbi) {
        personDAO = jdbi.onDemand(PersonDAO.class);
        personDAO.createPersonTable(); // creating DB table 'person' by NEED!
    }

    @GET
    public Response getAllPersons() {
        List<PersonDTO> persons = personDAO.findAllPersons();
        if (persons != null) {
            return Response.ok(persons).build();
        } else {
            System.out.println("============== else-branch of getAllPersons() method: Something went wrong... ==============");
            return Response.status(Status.NOT_FOUND).build();
        }
    }


    @GET
    @Path("/{id}")
    public Response getPersonById(@PathParam("id") Long id) {
        PersonDTO personDTO = personDAO.findPersonById(id);
        if (personDTO != null) {
            return Response.ok(personDTO).build();
        } else {
            System.out.println("============== else-branch of getPersonById(..) method: Something went wrong... ==============");
            return Response.status(Status.NOT_FOUND).build();
        }
    }


//    @POST
//    public Response savePerson(PersonDTO personDTO) {
//        // TODO: implement proper SAFE check (1st findAllPersons and then findPersonById)
//        PersonDTO savedPerson = personDAO.savePerson(personDTO.getFirstName(), personDTO.getLastName());
//        if (savedPerson != null) {
//            return Response.ok(savedPerson).build();
//        } else {
//            System.out.println("============== else-branch: Something went wrong... ==============");
//            return Response.status(Status.NOT_ACCEPTABLE).build();
//        }
//    }

    @POST
    public Response savePerson(PersonDTO personDTO) throws URISyntaxException {
        // TODO: implement proper SAFE check (1st findAllPersons and then findPersonById)
        int savedValue = personDAO.savePersonInt(personDTO.getFirstName(), personDTO.getLastName());
        if (savedValue != 0) {
            return Response.created(new URI(String.valueOf(savedValue))).build();
        } else {
            System.out.println("============== else-branch of savePerson(..) method: Something went wrong... ==============");
            return Response.status(Status.NOT_ACCEPTABLE).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response updatePerson(@PathParam("id") Long id, PersonDTO personDTO) {
        // TODO: implement proper SAFE check (1st findAllPersons and then findPersonById)
        PersonDTO searchedPerson = personDAO.findPersonById(id);
        if (searchedPerson != null) {
            boolean isPersonUpdated = personDAO.updatePerson(id, personDTO.getFirstName(), personDTO.getLastName());
            return Response.ok(isPersonUpdated).build();
        } else {
            System.out.println("============== else-branch of updatePerson(..) method: Something went wrong... ==============");
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deletePerson(@PathParam("id") Long id) {
        PersonDTO personDTO = personDAO.findPersonById(id);
        if (personDTO != null) {
            boolean isDeleted = personDAO.deletePerson(id);
            if (isDeleted) {
                return Response.ok(isDeleted).build();
            } else {
                System.out.println("============== else-branch of deletePerson(..) method: Something went wrong... ==============");
                return Response.status(Status.NOT_ACCEPTABLE).build();
            }
        } else {
            System.out.println("Person with id = " + id + " has not been found!");
            return Response.status(Status.BAD_REQUEST).build();
        }
    }

}

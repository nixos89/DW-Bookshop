package com.nikolas.master_thesis.resources;

import com.nikolas.master_thesis.db.OrderDAO;
import org.jdbi.v3.core.Jdbi;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
public class OrderResource {

    private final OrderDAO orderDAO;

    public OrderResource(Jdbi jdbi) {
        this.orderDAO = jdbi.onDemand(OrderDAO.class);
        orderDAO.createTableOrder();
        orderDAO.createOrderItemTable();
    }

    @GET
    public Response getAllOrders() {
//        List<OrderDTO> orders = orderDAO.getAllOrders();
//        if (orders != null) {
//            return Response.ok(orders).build();
//        } else {
//            return Response.status(Response.Status.NOT_FOUND).build();
//        }
        return null;
    }
}

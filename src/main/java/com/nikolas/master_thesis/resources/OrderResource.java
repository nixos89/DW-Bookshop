package com.nikolas.master_thesis.resources;

import com.nikolas.master_thesis.api.OrderListDTO;
import com.nikolas.master_thesis.api.OrderReportDTO;
import com.nikolas.master_thesis.api.OrderResponseDTO;
import com.nikolas.master_thesis.service.OrderService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
public class OrderResource {

    private final OrderService orderService;

    @Inject
    public OrderResource(OrderService orderService) {
        this.orderService = orderService;
    }

    @POST
    public Response saveOrder(OrderListDTO orderRequest, @QueryParam(value = "username") String username){
        OrderResponseDTO orderResponseDTO = orderService.addOrder(orderRequest, username);
        if(orderResponseDTO != null) {
            return Response.ok(orderResponseDTO).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }


    @GET
    public Response getAllOrders() {
        OrderReportDTO orderReportDTO = orderService.getAllOrders();
        if (orderReportDTO != null) {
            return Response.ok(orderReportDTO).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
}

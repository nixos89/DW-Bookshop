package com.nikolas.master_thesis.resources;

import com.codahale.metrics.annotation.Timed;
import com.nikolas.master_thesis.api.OrderListDTO;
import com.nikolas.master_thesis.api.OrderReportDTO;
import com.nikolas.master_thesis.api.OrderResponseDTO;
import com.nikolas.master_thesis.service.OrderService;
import com.nikolas.master_thesis.service.UserService;
import com.nikolas.master_thesis.util.DWBException;
import org.apache.http.HttpStatus;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
public class OrderResource {

    private final OrderService orderService;
    private final UserService userService;

    public OrderResource(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }


    @POST
    @Timed
    public Response saveOrder(OrderListDTO orderRequest, @QueryParam(value = "username") String username) throws DWBException {
        if (userService.getUserByUserName(username) == null) {
            throw new DWBException(HttpStatus.SC_UNAUTHORIZED, "Error, you (" + username + ") are not authorized to perform this action!");
        }
        if (orderRequest == null) {
            throw new DWBException(HttpStatus.SC_NOT_ACCEPTABLE, "Error, request body is empty! Please fill all fields for saving order!");
        }
        OrderResponseDTO orderResponseDTO = orderService.addOrder(orderRequest, username);
        if (orderResponseDTO != null) {
            return Response.ok(orderResponseDTO).build();
        } else {
            throw new DWBException(HttpStatus.SC_BAD_REQUEST, "Error, order can not be saved! Check for all fields.");
        }
    }


    @GET
    @Timed
    public Response getAllOrders() throws DWBException {
        OrderReportDTO orderReportDTO = orderService.getAllOrders();
        if (orderReportDTO != null) {
            return Response.ok(orderReportDTO).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
}

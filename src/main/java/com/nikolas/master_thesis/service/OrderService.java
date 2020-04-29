package com.nikolas.master_thesis.service;

import com.nikolas.master_thesis.api.AddOrderDTO;
import com.nikolas.master_thesis.api.OrderDTO;
import com.nikolas.master_thesis.api.OrderItemDTO;
import com.nikolas.master_thesis.api.OrderResponseDTO;
import com.nikolas.master_thesis.core.Order;
import com.nikolas.master_thesis.core.OrderItem;
import com.nikolas.master_thesis.db.OrderDAO;
import org.jdbi.v3.core.Jdbi;

import java.util.ArrayList;
import java.util.List;

public class OrderService {

    private final OrderDAO orderDAO;

    public OrderService(Jdbi jdbi) {
        this.orderDAO = jdbi.onDemand(OrderDAO.class);
        orderDAO.createTableOrder();
        orderDAO.createOrderItemTable();
    }

    public OrderResponseDTO addOrder(AddOrderDTO addOrderDTO) {
        // TODO: implement addOrder() method
        return null;
    }


    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderDAO.getAllOrders();
        List<OrderDTO> orderDTOS = new ArrayList<>();
        for (Order order : orders) {
            List<OrderItem> orderItems = orderDAO.getAllOrderItemsByOrderId(order.getOrderId());
            List<OrderItemDTO> orderItemDTOList = new ArrayList<>();
            // TODO: finish implementing getAllOrders() method

//            OrderDTO orderDTO = new OrderDTO(
//                    order.getOrderId(),
//                    order.getTotal(),
//                    order.getOrderDate(),
//
//                    order
//                    );
        }
        return null;
    }

}

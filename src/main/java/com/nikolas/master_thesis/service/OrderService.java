package com.nikolas.master_thesis.service;

import com.nikolas.master_thesis.api.*;
import com.nikolas.master_thesis.core.*;
import com.nikolas.master_thesis.db.*;
import com.nikolas.master_thesis.mapstruct_mappers.BookMSMapper;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Handles;
import org.jdbi.v3.core.Jdbi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

public class OrderService {

    private final BookMSMapper bookMSMapper;
    private final Jdbi jdbi;

    public OrderService(Jdbi jdbi, BookMSMapper bookMSMapper) {
        this.bookMSMapper = bookMSMapper;
        this.jdbi = jdbi;
    }


    public OrderResponseDTO addOrder(OrderListDTO orderRequest, String username) {
        Handle handle = jdbi.open();
        try {
            Set<OrderItem> orderItems = new HashSet<>();
            Order order = new Order();
            handle.begin();
            handle.getConnection().setAutoCommit(false);
            if (orderRequest != null) {
                UserDAO userDAO = handle.attach(UserDAO.class);
                User user = userDAO.findUserByUsername(username);
                if (user == null) {
                    throw new Exception("Error, user with username: " + username + " doesn't exist in DB!");
                }
                BookDAO bookDAO = handle.attach(BookDAO.class);
                for (AddOrderDTO addOrder : orderRequest.getOrders()) {
                    Book book = bookDAO.getBookById(addOrder.getBookId());
                    if (book == null) {
                        throw new Exception("Error, book with id " + addOrder.getBookId() + " does NOT exist in DB!");
                    } else if (addOrder.getAmount() > book.getAmount()) {
                        throw new Exception("Error, it's ONLY possible (for book '" + book.getTitle() + "', id = "
                                + book.getBookId() + ") to order up to " + book.getAmount() + " copies!");
                    } else {
                        book.setAmount(book.getAmount() - addOrder.getAmount());
                        order.setTotal(orderRequest.getTotalPrice());
                        order.setOrderDate(new Timestamp(System.currentTimeMillis()));
                        order.setUser(user);
                        order.setOrderItems(orderItems);
                        if (!bookDAO.updateBookDTO(book.getBookId(), book.getTitle(), book.getPrice(), book.getAmount(), book.isDeleted())) {
                            throw new Exception("Error, book has NOT been updated!");
                        }

                        OrderItem orderItem = new OrderItem();
                        orderItem.setAmount(addOrder.getAmount());
                        orderItem.setBook(book);
                        orderItem.setOrder(order);
                        orderItems.add(orderItem);
                    }
                }
                OrderDAO orderDAO = handle.attach(OrderDAO.class);
                order.setOrderItems(orderItems);
                order = orderDAO.createOrder(order.getTotal(), order.getOrderDate(), user.getUserId());
                OrderItemDAO orderItemDAO = handle.attach(OrderItemDAO.class);
                for (OrderItem oi : orderItems) {
                    orderItemDAO.createOrderItem(oi.getAmount(), oi.getBook().getBookId(), order.getOrderId());
                }
                handle.commit();
                return new OrderResponseDTO(order.getOrderId());
            } else {
                throw new Exception("Error, orderRequest is EMPTY!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            handle.rollback();
            return null;
        } finally {
            handle.close();
        }
    }


    public OrderReportDTO getAllOrders() {
        Handle handle = jdbi.open();
        try {
            List<OrderDTO> orderDTOList = new LinkedList<>();
            List<OrderItemDTO> orderItemDTOList = new ArrayList<>();
            handle.begin();
            handle.getConnection().setAutoCommit(false);
            OrderDAO orderDAO = handle.attach(OrderDAO.class);
            List<Order> orders = orderDAO.getAllOrders();
            OrderDTO orderDTO = new OrderDTO();
            if (orders != null && !orders.isEmpty()) {
                double orderPrice;
                AuthorDAO authorDAO = handle.attach(AuthorDAO.class);
                BookDAO bookDAO = handle.attach(BookDAO.class);
                CategoryDAO categoryDAO = handle.attach(CategoryDAO.class);
                OrderItemDAO orderItemDAO = handle.attach(OrderItemDAO.class);
                for (Order order : orders) {
                    Date orderDate = order.getOrderDate();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String dateString;
                    if (orderDate == null) {
                        throw new Exception("Error, date formatting failed!");
                    }
                    dateString = sdf.format(orderDate);
                    orderPrice = 0.0;
                    List<OrderItem> orderItems = orderItemDAO.getAllOrderItemsByOrderId(order.getOrderId());
                    for (OrderItem oi : orderItems) {
                        Long bookId = oi.getBook().getBookId();
                        Book book = bookDAO.getBookById(bookId);
                        Set<Author> authors = new HashSet<>(authorDAO.getAuthorsByBookId(bookId));
                        Set<Category> categories = new HashSet<>(categoryDAO.getCategoriesByBookId(bookId));
                        book.setAuthors(authors);
                        book.setCategories(categories);

                        BookDTO bookDTO = bookMSMapper.fromBook(book);
                        BigDecimal bd = BigDecimal.valueOf((book.getPrice() * oi.getAmount())).setScale(2, RoundingMode.HALF_UP);
                        double booksPriceNew = bd.doubleValue();
                        orderPrice += booksPriceNew;

                        OrderItemDTO oiDTO = new OrderItemDTO(oi.getOrderItemId(), oi.getAmount(), bookDTO, oi.getOrder().getOrderId(), booksPriceNew);
                        orderItemDTOList.add(oiDTO);
                    }
                    BigDecimal bd2 = BigDecimal.valueOf(orderPrice).setScale(2, RoundingMode.HALF_UP);
                    double orderPriceNew = bd2.doubleValue();
                    orderDTO = new OrderDTO(order.getOrderId(), orderPriceNew, dateString, orderItemDTOList, new UserDTO(order.getUser()));
                    orderDTOList.add(orderDTO);
                    orderItemDTOList = new LinkedList<>();
                }

            }
            OrderReportDTO orderReportDTO = new OrderReportDTO();
            orderReportDTO.setOrders(orderDTOList);
            handle.commit();
            return orderReportDTO;
        } catch (Exception e) {
            e.printStackTrace();
            handle.rollback();
            return null;
        } finally {
            handle.close();
        }
    }

}

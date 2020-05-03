package com.nikolas.master_thesis.service;

import com.nikolas.master_thesis.api.*;
import com.nikolas.master_thesis.core.*;
import com.nikolas.master_thesis.db.*;
import com.nikolas.master_thesis.mapstruct_mappers.BookMSMapper;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

public class OrderService {

    //    private final OrderDAO orderDAO;
    private final BookMSMapper bookMSMapper;
    private final Jdbi jdbi;
//    private final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    public OrderService(Jdbi jdbi, BookMSMapper bookMSMapper) {
        this.bookMSMapper = bookMSMapper;
        this.jdbi = jdbi;
//        this.orderDAO = jdbi.onDemand(OrderDAO.class);
//        orderDAO.createOrderTable();
//        orderDAO.createOrderItemTable();
    }


    public OrderResponseDTO addOrder(OrderListDTO orderRequest, String username) {
        Handle handle = jdbi.open();
        try {
            Set<OrderItem> orderItems = new HashSet<>();
            Order order = new Order();
            if (orderRequest != null) {
                BookDAO bookDAO = handle.attach(BookDAO.class);
                UserDAO userDAO = handle.attach(UserDAO.class);
                OrderDAO orderDAO = handle.attach(OrderDAO.class);
                OrderItemDAO orderItemDAO = handle.attach(OrderItemDAO.class);
                handle.begin();
                User user = userDAO.findUserByUsername(username);//.orElse(null);
                for (AddOrderDTO addOrder : orderRequest.getOrders()) {
                    Book book = bookDAO.getBookById(addOrder.getBookId());
                    if (book == null) {
                        throw new Exception("Error, book with id " + addOrder.getBookId() + " does NOT exist in DB!");
                    } else if (addOrder.getAmount() > book.getAmount()) {
                        throw new Exception("Error, it's ONLY possible (for book '" + book.getTitle() + "', id = "
                                + book.getBookId() + ") to order up to " + book.getAmount() + " copies!");
                    } else {
                        if (user == null) {
                            throw new Exception("Error, user with username: " + username + " doesn't exist in DB!");
                        }
                        book.setAmount(book.getAmount() - addOrder.getAmount());
                        order.setTotal(orderRequest.getTotalPrice());
                        order.setOrderDate(new Timestamp(System.currentTimeMillis()));
                        order.setUser(user);
                        order.setOrderItems(orderItems);

                        OrderItem orderItem = new OrderItem();
                        orderItem.setAmount(addOrder.getAmount());
                        orderItem.setBook(book);
                        orderItem.setOrder(order);
                        orderItems.add(orderItem);
                    }
                }
                order.setOrderItems(orderItems);
                order = orderDAO.createOrder(order.getTotal(), order.getOrderDate(), user.getUserId());
                for (OrderItem oi : orderItems) {
                    orderItemDAO.createOrderItem(oi.getAmount(), oi.getBook().getBookId(), order.getOrderId());
                }
                handle.commit();
                return new OrderResponseDTO(order.getOrderId());
            } else {
                handle.rollback();
                throw new Exception("Error, orderRequest is EMPTY!");
            }
        } catch (Exception e) {
            handle.rollback();
            e.printStackTrace();
            return null;
        } finally {
            handle.close();
        }
    }


    public OrderReportDTO getAllOrders() {
        Handle handle = jdbi.open();
        OrderDAO orderDAO = handle.attach(OrderDAO.class);
        OrderItemDAO orderItemDAO = handle.attach(OrderItemDAO.class);
        AuthorDAO authorDAO = handle.attach(AuthorDAO.class);
        BookDAO bookDAO = handle.attach(BookDAO.class);
        CategoryDAO categoryDAO = handle.attach(CategoryDAO.class);
        try {
            handle.begin();
            List<OrderDTO> orderDTOList = new LinkedList<>();
            List<OrderItemDTO> orderItemDTOList = new ArrayList<>();
            List<Order> orders = orderDAO.getAllOrders();
            OrderDTO orderDTO = new OrderDTO();
            if (orders != null && !orders.isEmpty()) {
                double orderPrice;
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

                        BookDTO2 bookDTO2 = bookMSMapper.fromBook(book);
                        BigDecimal bd = BigDecimal.valueOf( (book.getPrice() * oi.getAmount())).setScale(2, RoundingMode.HALF_UP);
                        double booksPriceNew = bd.doubleValue();
                        orderPrice += booksPriceNew;

                        OrderItemDTO oiDTO = new OrderItemDTO(oi.getOrderItemId(), oi.getAmount(), bookDTO2, oi.getOrder().getOrderId(), booksPriceNew);
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
            handle.rollback();
            e.printStackTrace();
            return null;
        } finally {
            handle.close();
        }
    }

}

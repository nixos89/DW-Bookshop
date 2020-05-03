package com.nikolas.master_thesis.db;

import com.nikolas.master_thesis.api.BookDTO;
import com.nikolas.master_thesis.core.Book;
import com.nikolas.master_thesis.core.Order;
import com.nikolas.master_thesis.core.OrderItem;
import com.nikolas.master_thesis.reducers.OrderItemBookReducer;
import com.nikolas.master_thesis.reducers.OrderItemOrderReducer;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.statement.UseRowReducer;

import java.util.List;

public interface OrderItemDAO {

    // FIXME: might need to create 2 Reducers (OrderItemBookReducer and OrderItemOrderReducer)
    @GetGeneratedKeys
    @SqlUpdate("INSERT INTO order_item(amount, book_id, order_id) VALUES(:amount, :bookId, :orderId)")
    @RegisterBeanMapper(OrderItem.class)
    @RegisterBeanMapper(Book.class)
    @RegisterBeanMapper(Order.class)
    OrderItem createOrderItem(@Bind("amount") int amount, @Bind("bookId") Long bookId, @Bind("orderId") Long orderId);

    @RegisterBeanMapper(value = OrderItem.class, prefix = "oi")
    @RegisterBeanMapper(value = Order.class, prefix = "o")
    @RegisterBeanMapper(value = Book.class, prefix = "b")
    @UseRowReducer(OrderItemOrderReducer.class)
    @SqlQuery("SELECT oi.order_item_id oi_order_item_id, oi.amount oi_amount, o.order_id o_order_id, b.book_id b_book_id " +
            "FROM order_item AS oi " +
            "LEFT JOIN orders AS o ON oi.order_id = o.order_id " +
            "LEFT JOIN book AS b ON oi.book_id = b.book_id " +
            "WHERE oi.order_id = :orderId")
    List<OrderItem> getAllOrderItemsByOrderId(@Bind("orderId") Long orderId);

}

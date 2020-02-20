package com.nikolas.master_thesis.db;

import com.nikolas.master_thesis.api.OrderDTO;
import com.nikolas.master_thesis.core.Order;
import com.nikolas.master_thesis.core.OrderItem;
import com.nikolas.master_thesis.core.User;
import com.nikolas.master_thesis.reducers.UserOrderReducer;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.statement.UseRowReducer;

import java.util.Date;
import java.util.List;

public interface OrderDAO {

    @RegisterBeanMapper(Order.class)
    @SqlUpdate("CREATE TABLE IF NOT EXISTS Orders (order_id BIGSERIAL PRIMARY KEY, total double precision, order_date DATE, user_id INTEGER REFERENCES Users(user_id) )")
    void createTableOrder();

    @RegisterBeanMapper(OrderItem.class)
    @SqlUpdate("CREATE TABLE IF NOT EXISTS Order_Item ( order_item_id BIGSERIAL PRIMARY KEY, amount INTEGER, book_id BIGINT REFERENCES Book(book_id), order_id BIGINT REFERENCES Orders(order_id) )")
    void createOrderItemTable();

    @RegisterBeanMapper(value= Order.class, prefix = "o")
    @RegisterBeanMapper(value= User.class, prefix = "u")
    @SqlQuery("SELECT o.order_id o_id, o.total o_total, o.order_date o_order_date, u.user_id u_id, u.first_name u_first_name, u.last_name u_last_name, " +
            "u.username u_username, u.email u_email, u.role_id u_role_id FROM Order LEFT JOIN Users ON Users.user_id = Order.user_id")
    @UseRowReducer(UserOrderReducer.class)
    List<OrderDTO> getAllOrders();


    @SqlUpdate("INSERT INTO Order(total, order_date, user_id) VALUES (?, ?, ?)")
    @GetGeneratedKeys
    OrderDTO addOrder(double total, Date orderDate, Long userId);

}

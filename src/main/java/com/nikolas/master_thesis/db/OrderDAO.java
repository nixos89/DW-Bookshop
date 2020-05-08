package com.nikolas.master_thesis.db;

import com.nikolas.master_thesis.core.Order;
import com.nikolas.master_thesis.core.OrderItem;
import com.nikolas.master_thesis.core.User;
import com.nikolas.master_thesis.reducers.OrderUserReducer;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.statement.UseRowReducer;

import java.util.Date;
import java.util.List;

public interface OrderDAO {

    @RegisterBeanMapper(Order.class)
    @SqlUpdate("CREATE TABLE IF NOT EXISTS Orders (order_id BIGSERIAL PRIMARY KEY, total double precision, " +
            "order_date TIMESTAMP, user_id BIGINT REFERENCES Users(user_id) )")
    void createOrderTable();

    @RegisterBeanMapper(value= Order.class, prefix = "o")
    @RegisterBeanMapper(value= User.class, prefix = "u")
    @SqlQuery("SELECT o.order_id o_order_id, o.total o_total, o.order_date o_order_date, u.user_id u_user_id, u.first_name u_first_name, " +
            "u.last_name u_last_name, u.username u_username, u.email u_email, u.role_id u_role_id " +
            "FROM Orders o LEFT JOIN Users u ON o.user_id = u.user_id ORDER BY o.order_id ASC")
    @UseRowReducer(OrderUserReducer.class)
    List<Order> getAllOrders();


    @GetGeneratedKeys
    @SqlUpdate("INSERT INTO orders(total, order_date, user_id) VALUES(:total, :orderDate, :userId)")
    @RegisterBeanMapper(Order.class)
    @RegisterBeanMapper(User.class)
    Order createOrder(@Bind("total") double total, @Bind("orderDate") Date orderDate, @Bind("userId") Long userId);

}

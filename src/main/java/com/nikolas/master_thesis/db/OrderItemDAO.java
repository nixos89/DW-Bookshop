package com.nikolas.master_thesis.db;

import com.nikolas.master_thesis.core.OrderItem;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface OrderItemDAO {

    @RegisterBeanMapper(OrderItem.class)
    @SqlUpdate("CREATE TABLE IF NOT EXISTS OrderItem ( order_item_id BIGSERIAL PRIMARY KEY, amount INTEGER, book_id BIGINT REFERENCES Book(id), order_id BIGINT REFERENCES Order(order_id) )")
    void createOrderItemTable();

}

package com.nikolas.master_thesis.service;

import com.nikolas.master_thesis.api.*;
import com.nikolas.master_thesis.core.*;
import com.nikolas.master_thesis.db.*;
import com.nikolas.master_thesis.mapstruct_mappers.BookMSMapper;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Handles;
import org.jdbi.v3.core.Jdbi;
import org.jvnet.hk2.annotations.Contract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Contract
public interface OrderService {

    OrderResponseDTO addOrder(OrderListDTO orderRequest, String username);
    OrderReportDTO getAllOrders();

}

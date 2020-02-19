package com.dev.cinema.service;

import com.dev.cinema.model.Order;
import com.dev.cinema.model.User;

import java.util.List;

public interface OrderService {
    Order completeOrder(User user);

    List<Order> getOrderHistory(User user);

    Order getById(Long id);
}

package com.dev.cinema.service.impl;

import com.dev.cinema.dao.OrderDao;
import com.dev.cinema.dao.ShoppingCartDao;
import com.dev.cinema.lib.Inject;
import com.dev.cinema.lib.Service;
import com.dev.cinema.model.Order;
import com.dev.cinema.model.ShoppingCart;
import com.dev.cinema.model.User;
import com.dev.cinema.service.OrderService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Inject
    private OrderDao orderDao;

    @Inject
    private ShoppingCartDao shoppingCartDao;

    @Override
    public Order completeOrder(ShoppingCart shoppingCart) {
        Order order = new Order();
        order.setTickets(shoppingCart.getTickets());
        order.setUser(shoppingCart.getUser());
        order.setOrderDate(LocalDateTime.now());

        ShoppingCart newShoppingCart = shoppingCartDao.getByUser(shoppingCart.getUser());
        newShoppingCart.getTickets().clear();
        shoppingCartDao.update(newShoppingCart);

        return orderDao.add(order);
    }

    @Override
    public List<Order> getOrderHistory(User user) {
        return orderDao.getAllUserOrders(user);
    }
}

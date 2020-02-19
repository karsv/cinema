package com.dev.cinema.controllers;

import com.dev.cinema.dto.OrderResponseDto;
import com.dev.cinema.dto.ShoppingCartResponseDto;
import com.dev.cinema.dto.UserRequestDto;
import com.dev.cinema.model.Order;
import com.dev.cinema.model.ShoppingCart;
import com.dev.cinema.model.User;
import com.dev.cinema.service.OrderService;
import com.dev.cinema.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;

    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @PostMapping("/complete")
    private void completeOrder(@RequestBody ShoppingCartResponseDto shoppingCartDto) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(shoppingCartDto.getUser());
        shoppingCart.setTickets(shoppingCartDto.getTickets());
        orderService.completeOrder(shoppingCart);
    }

    @PostMapping("/")
    private List<OrderResponseDto> getAllOrders(@RequestBody UserRequestDto userRequestDto) {
        User user = userService.findByEmail(userRequestDto.getEmail());
        return orderService.getOrderHistory(user)
                .stream()
                .map(this::getOrderToOrderDto)
                .collect(Collectors.toList());
    }

    private OrderResponseDto getOrderToOrderDto(Order order) {
        OrderResponseDto orderDto = new OrderResponseDto();
        orderDto.setOrderDate(order.getOrderDate());
        orderDto.setTickets(order.getTickets());
        orderDto.setUser(order.getUser());
        return orderDto;
    }
}

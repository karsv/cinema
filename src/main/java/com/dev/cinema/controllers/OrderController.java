package com.dev.cinema.controllers;

import com.dev.cinema.dto.OrderResponseDto;
import com.dev.cinema.dto.TicketDto;
import com.dev.cinema.dto.UserRequestDto;
import com.dev.cinema.dto.UserResponseDto;
import com.dev.cinema.model.Order;
import com.dev.cinema.model.Ticket;
import com.dev.cinema.model.User;
import com.dev.cinema.service.MovieSessionService;
import com.dev.cinema.service.OrderService;
import com.dev.cinema.service.UserService;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final OrderService orderService;
    private final UserService userService;
    private final MovieSessionService movieSessionService;

    public OrderController(OrderService orderService, UserService userService,
                           MovieSessionService movieSessionService) {
        this.orderService = orderService;
        this.userService = userService;
        this.movieSessionService = movieSessionService;
    }

    @PostMapping("/complete")
    private Order completeOrder(@RequestBody UserResponseDto userResponseDto) {
        return orderService.completeOrder(userService.findByEmail(userResponseDto.getEmail()));
    }

    @PostMapping("/")
    private List<OrderResponseDto> getAllOrders(@RequestBody UserResponseDto userRequestDto) {
        User user = userService.findByEmail(userRequestDto.getEmail());
        return orderService.getOrderHistory(user)
                .stream()
                .map(this::getOrderToOrderResponseDto)
                .collect(Collectors.toList());
    }

    private OrderResponseDto getOrderToOrderResponseDto(Order order) {
        OrderResponseDto orderDto = new OrderResponseDto();
        orderDto.setUserId(order.getUser().getId());
        orderDto.setTicketDtoList(order.getTickets()
                .stream()
                .map(this::getTicketToTicketResponseDto)
                .collect(Collectors.toList()));
        return orderDto;
    }

    private TicketDto getTicketToTicketResponseDto(Ticket ticket) {
        TicketDto ticketDto = new TicketDto();
        ticketDto.setMovieTitle(ticket.getMovie().getTitle());
        ticketDto.setShowTime(ticket.getShowtime().format(FORMATTER));
        ticketDto.setCinemaHallDescription(ticket.getCinemaHall().getDescription());
        return ticketDto;
    }
}


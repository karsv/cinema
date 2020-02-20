package com.dev.cinema.controllers;

import com.dev.cinema.dto.MovieSessionRequestDto;
import com.dev.cinema.dto.ShoppingCartDto;
import com.dev.cinema.dto.TicketDto;
import com.dev.cinema.model.MovieSession;
import com.dev.cinema.model.ShoppingCart;
import com.dev.cinema.model.Ticket;
import com.dev.cinema.model.User;
import com.dev.cinema.service.CinemaHallService;
import com.dev.cinema.service.MovieService;
import com.dev.cinema.service.ShoppingCartService;
import com.dev.cinema.service.UserService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shoppingcarts")
public class ShoppingCartController {
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final ShoppingCartService shoppingCartService;
    private final UserService userService;
    private final CinemaHallService cinemaHallService;
    private final MovieService movieService;

    public ShoppingCartController(ShoppingCartService shoppingCartService,
                                  UserService userService,
                                  CinemaHallService cinemaHallService,
                                  MovieService movieService) {
        this.shoppingCartService = shoppingCartService;
        this.userService = userService;
        this.cinemaHallService = cinemaHallService;
        this.movieService = movieService;
    }

    @GetMapping("/byuser")
    private ShoppingCartDto getShoppingCartByUserId(Long userId) {
        User user = userService.findById(userId);
        ShoppingCart shoppingCart = shoppingCartService.getByUser(user);
        ShoppingCartDto shoppingCartDto = new ShoppingCartDto();
        shoppingCartDto.setUserId(userId);
        shoppingCartDto.setTickets(shoppingCart.getTickets()
                .stream()
                .map(this::getTicketToTicketDto)
                .collect(Collectors.toList()));
        return shoppingCartDto;
    }

    @PostMapping("/addmoviesession")
    public void addMovieSession(Long userId, @RequestBody MovieSessionRequestDto movieSessionDto) {
        MovieSession movieSession = new MovieSession();
        movieSession.setCinemaHall(cinemaHallService.getById(movieSessionDto.getCinemaHallId()));
        movieSession.setMovie(movieService.getById(movieSessionDto.getMovieId()));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        movieSession.setShowTime(LocalDateTime.parse(movieSessionDto.getShowTime(), formatter));
        User user = userService.findById(userId);
        shoppingCartService.addSession(movieSession, user);
    }

    private TicketDto getTicketToTicketDto(Ticket ticket) {
        TicketDto ticketDto = new TicketDto();
        ticketDto.setMovieTitle(ticket.getMovie().getTitle());
        ticketDto.setShowTime(ticket.getShowtime().format(FORMATTER));
        ticketDto.setCinemaHallDescription(ticket.getCinemaHall().getDescription());
        return ticketDto;
    }
}

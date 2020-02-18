package com.dev.cinema.controllers;

import com.dev.cinema.dto.MovieSessionDto;
import com.dev.cinema.dto.ShoppingCartDto;
import com.dev.cinema.model.MovieSession;
import com.dev.cinema.model.ShoppingCart;
import com.dev.cinema.model.User;
import com.dev.cinema.service.ShoppingCartService;
import com.dev.cinema.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shoppingcarts")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    private final UserService userService;

    public ShoppingCartController(ShoppingCartService shoppingCartService,
                                  UserService userService) {
        this.shoppingCartService = shoppingCartService;
        this.userService = userService;
    }

    @GetMapping("/byuser")
    private ShoppingCartDto getShoppingCartByUserId(Long userId) {
        User user = userService.findById(userId);
        ShoppingCart shoppingCart = shoppingCartService.getByUser(user);
        ShoppingCartDto shoppingCartDto = new ShoppingCartDto();
        shoppingCartDto.setUser(user);
        shoppingCartDto.setTickets(shoppingCart.getTickets());
        return shoppingCartDto;
    }

    @PostMapping("/addmoviesession")
    public void addMovieSession(@RequestBody MovieSessionDto movieSessionDto,
                                @RequestParam(value = "user_id") Long userId) {
        MovieSession movieSession = new MovieSession();
        movieSession.setCinemaHall(movieSessionDto.getCinemaHall());
        movieSession.setMovie(movieSessionDto.getMovie());
        movieSession.setShowTime(movieSessionDto.getShowTime());
        User user = userService.findById(userId);
        shoppingCartService.addSession(movieSession, user);
    }
}

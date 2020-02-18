package com.dev.cinema.service.impl;

import com.dev.cinema.exception.AuthenticationException;
import com.dev.cinema.exception.DataProcessingException;
import com.dev.cinema.model.User;
import com.dev.cinema.service.AuthenticationService;
import com.dev.cinema.service.ShoppingCartService;
import com.dev.cinema.service.UserService;
import com.dev.cinema.util.EmailUtil;
import com.dev.cinema.util.HashUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private UserService userService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Override
    public User login(String email, String password) throws AuthenticationException,
            DataProcessingException {
        User user = userService.findByEmail(email);
        if (user == null || !user.getPassword()
                .equals(HashUtil.hashPassword(password, user.getSalt()))) {
            throw new AuthenticationException("Wrong authentification parameters!");
        }
        return user;
    }

    @Override
    public User register(String email, String password) throws DataProcessingException {
        if (!EmailUtil.isValid(email)) {
            throw new DataProcessingException("Wrong email format!");
        }
        User user = new User();
        user.setEmail(email);
        user.setSalt(HashUtil.getSalt());
        user.setPassword(HashUtil.hashPassword(password, user.getSalt()));
        User userDb = userService.add(user);
        shoppingCartService.registerNewShoppingCart(userDb);
        return userDb;
    }
}

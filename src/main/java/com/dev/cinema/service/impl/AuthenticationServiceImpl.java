package com.dev.cinema.service.impl;

import com.dev.cinema.exception.AuthenticationException;
import com.dev.cinema.exception.DataProcessingException;
import com.dev.cinema.exception.RegisterException;
import com.dev.cinema.lib.Inject;
import com.dev.cinema.model.User;
import com.dev.cinema.service.AuthenticationService;
import com.dev.cinema.service.UserService;
import com.dev.cinema.util.EmailUtil;
import com.dev.cinema.util.HashUtil;

public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

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
            throw new RegisterException("Wrong email format!");
        }

        if (userService.findByEmail(email) != null) {
            throw new RegisterException("There is user with such email!");
        }

        User user = new User();
        user.setEmail(email);
        user.setSalt(HashUtil.getSalt());
        user.setPassword(HashUtil.hashPassword(password, user.getSalt()));
        return userService.add(user);
    }
}

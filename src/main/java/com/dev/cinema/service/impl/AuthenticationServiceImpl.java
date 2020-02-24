package com.dev.cinema.service.impl;

import com.dev.cinema.dao.RoleDao;
import com.dev.cinema.exception.AuthenticationException;
import com.dev.cinema.exception.DataProcessingException;
import com.dev.cinema.model.User;
import com.dev.cinema.service.AuthenticationService;
import com.dev.cinema.service.ShoppingCartService;
import com.dev.cinema.service.UserService;
import com.dev.cinema.util.EmailUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private UserService userService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleDao roleDao;

    @Override
    public User login(String email, String password) throws AuthenticationException,
            DataProcessingException {
        User user = userService.findByEmail(email);
        if (user == null || !user.getPassword().equals(passwordEncoder.encode(password))) {
            throw new AuthenticationException("Wrong authentication parameters!");
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
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(roleDao.getRoleByName("USER"));
        User userDb = userService.add(user);
        shoppingCartService.registerNewShoppingCart(userDb);
        return userDb;
    }
}

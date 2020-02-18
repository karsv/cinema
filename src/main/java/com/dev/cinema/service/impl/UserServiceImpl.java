package com.dev.cinema.service.impl;

import com.dev.cinema.dao.UserDao;
import com.dev.cinema.exception.DataProcessingException;
import com.dev.cinema.model.User;
import com.dev.cinema.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User add(User user) throws DataProcessingException {
        return userDao.add(user);
    }

    @Override
    public User findByEmail(String email) throws DataProcessingException {
        return userDao.findByEmail(email);
    }
}

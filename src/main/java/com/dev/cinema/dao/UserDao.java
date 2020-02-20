package com.dev.cinema.dao;

import com.dev.cinema.exception.DataProcessingException;
import com.dev.cinema.model.User;

public interface UserDao {
    User add(User user) throws DataProcessingException;

    User findByEmail(String email) throws DataProcessingException;

    User findById(Long id) throws DataProcessingException;
}

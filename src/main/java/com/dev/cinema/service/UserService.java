package com.dev.cinema.service;

import com.dev.cinema.exception.DataProcessingException;
import com.dev.cinema.model.User;

import java.util.Optional;

public interface UserService {
    User add(User user) throws DataProcessingException;

    Optional<User> findByEmail(String email) throws DataProcessingException;
}

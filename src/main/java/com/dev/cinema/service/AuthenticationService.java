package com.dev.cinema.service;

import com.dev.cinema.exception.AuthenticationException;
import com.dev.cinema.exception.DataProcessingException;
import com.dev.cinema.model.User;

public interface AuthenticationService {
    User login(String email, String password)
            throws AuthenticationException, DataProcessingException;

    User register(String email, String password) throws DataProcessingException;
}

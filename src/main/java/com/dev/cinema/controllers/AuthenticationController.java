package com.dev.cinema.controllers;

import com.dev.cinema.dto.UserRegistrationDto;
import com.dev.cinema.dto.UserRequestDto;
import com.dev.cinema.exception.AuthenticationException;
import com.dev.cinema.exception.DataProcessingException;
import com.dev.cinema.service.AuthenticationService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class AuthenticationController {
    private static final Logger LOGGER = LogManager.getLogger(AuthenticationController.class);

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public String login(@RequestBody UserRequestDto userRequestDto) {
        try {
            authenticationService.login(userRequestDto.getEmail(), userRequestDto.getPassword());
        } catch (AuthenticationException e) {
            LOGGER.error("Wrong authentication parameters", e);
            return e.getMessage();
        }
        return "Login successful!";
    }

    @PostMapping("/registration")
    public String register(@RequestBody UserRegistrationDto userRegistrationDto) {
        try {
            if (!userRegistrationDto.getPassword()
                    .equals(userRegistrationDto.getRepeatPassword())) {
                throw new DataProcessingException("Passwords are different!");
            }
            authenticationService.register(userRegistrationDto.getEmail(),
                    userRegistrationDto.getPassword());
        } catch (DataProcessingException e) {
            return e.getMessage();
        }
        return "Registration successful!";
    }

}

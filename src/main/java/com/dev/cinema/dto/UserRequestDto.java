package com.dev.cinema.dto;

import com.dev.cinema.security.EmailValidation;

import javax.validation.constraints.NotNull;

public class UserRequestDto {
    @NotNull(message = "Email couldn't be null!")
    @EmailValidation
    private String email;
    @NotNull(message = "Password couldn't be null!")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

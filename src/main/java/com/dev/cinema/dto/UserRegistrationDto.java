package com.dev.cinema.dto;

import com.dev.cinema.security.EmailValidation;
import com.dev.cinema.security.PasswordEqualValidator;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@PasswordEqualValidator
public class UserRegistrationDto {
    @NotNull(message = "Email couldn't be null!")
    @Size(min = 3)
    @EmailValidation
    private String email;
    @NotNull(message = "Password couldn't be null!")
    private String password;
    @NotNull(message = "Repeated password couldn't be null!")
    private String repeatPassword;

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

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }
}

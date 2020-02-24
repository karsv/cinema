package com.dev.cinema.controllers;

import com.dev.cinema.dao.RoleDao;
import com.dev.cinema.model.Role;
import com.dev.cinema.model.User;
import com.dev.cinema.service.UserService;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class InitController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    private void postConstruct() {
        Role adminRole = new Role();
        adminRole.setRoleName("ADMIN");
        roleDao.add(adminRole);
        Role userRole = new Role();
        userRole.setRoleName("USER");
        roleDao.add(userRole);

        User user = new User();
        user.setEmail("admin@admin.com");
        user.setPassword(passwordEncoder.encode("123"));
        user.setRole(adminRole);
        userService.add(user);
    }
}

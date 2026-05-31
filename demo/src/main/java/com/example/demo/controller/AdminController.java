package com.example.demo.controller;

import com.example.demo.dto.LoginDTO;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    UserService  userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> visualizzaUser(){
        return userService.visualizzaUser();
    }
}

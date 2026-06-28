package com.example.demo.controller;

import com.example.demo.dto.RegisterDTO;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody RegisterDTO dto) {
        String token = userService.loginUser(dto);

        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDTO registerDTO){
        System.out.println("ciao");
        return ResponseEntity.ok(userService.registerUser(registerDTO));
    }

    @PostMapping("/test")
    public ResponseEntity<?> creaAdmin(){

        return ResponseEntity.ok(userService.creaAdmin());
    }
}
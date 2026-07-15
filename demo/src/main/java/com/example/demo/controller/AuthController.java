package com.example.demo.controller;

import com.example.demo.dto.LoginResponseDTO;
import com.example.demo.dto.RegisterDTO;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginUser(@RequestBody RegisterDTO dto,
                                                      HttpServletResponse response) {

        return ResponseEntity.ok(userService.loginUser(dto, response));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDTO registerDTO){
        return ResponseEntity.ok(userService.registerUser(registerDTO));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response){
        String message = userService.Logout(response);
        return  ResponseEntity.ok(message);
    }

    @PostMapping("/test")
    public ResponseEntity<?> creaAdmin(){

        return ResponseEntity.ok(userService.creaAdmin());
    }
}
package com.example.demo.service;

import com.example.demo.dto.LoginDTO;
import com.example.demo.entity.User;
import com.example.demo.repo.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,  PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String login(LoginDTO loginDTO) {

        User user = userRepository.findByUsername(loginDTO.getUsername()).orElseThrow(() -> new RuntimeException("utente non trovato"));

        if(!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("credenziali non valide");
        }

        return JwtUtil.generateToken(user.getUsername());
    }

    public String registerUser(LoginDTO loginDTO) {
        if(loginDTO.getUsername() == null || loginDTO.getPassword() == null) {
            throw new RuntimeException("credenziali non valide");
        }
        if(userRepository.findByUsername(loginDTO.getUsername()).isPresent()) {
            throw new RuntimeException("username non disponibile");
        }

        User user = new User();
        user.setUsername(loginDTO.getUsername());
        user.setPassword(passwordEncoder.encode(loginDTO.getPassword()));
    }
}
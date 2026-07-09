package com.example.demo.service;

import com.example.demo.dto.LoginResponseDTO;
import com.example.demo.dto.RegisterDTO;
import com.example.demo.entity.Ruolo;
import com.example.demo.entity.User;
import com.example.demo.repo.UserRepository;
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

    public LoginResponseDTO loginUser(RegisterDTO dto) {

        User user = userRepository.findByUsername(dto.getUsername()).orElseThrow(() -> new RuntimeException("utente non trovato"));

        if(!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("credenziali non valide");
        }

        String token = JwtUtil.generateToken(user.getUsername(), user.getRuolo().name());

        LoginResponseDTO dtoresponse = new LoginResponseDTO();
        dtoresponse.setToken(token);
        dtoresponse.setRuolo(user.getRuolo().name());
        dtoresponse.setUsername(user.getUsername());
        return dtoresponse;
    }

    public String registerUser(RegisterDTO registerDTO){
        if(registerDTO.getUsername() == null || registerDTO.getPassword() == null) {
            throw new RuntimeException("credenziali non valide");
        }
        if(userRepository.findByUsername(registerDTO.getUsername()).isPresent()) {
            throw new RuntimeException("username non disponibile");
        }

        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setRuolo(Ruolo.DIPENDENTE);
        userRepository.save(user);
        return JwtUtil.generateToken(user.getUsername(), user.getRuolo().name());
    }

    public User creaAdmin(){
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setRuolo(Ruolo.ADMIN);
        return userRepository.save(admin);
    }
}
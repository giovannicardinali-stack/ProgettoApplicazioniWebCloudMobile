package com.example.demo.service;

import com.example.demo.dto.LoginResponseDTO;
import com.example.demo.dto.RegisterDTO;
import com.example.demo.entity.Ruolo;
import com.example.demo.entity.User;
import com.example.demo.repo.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
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

    public LoginResponseDTO loginUser(RegisterDTO dto, HttpServletResponse response) {

        User user = userRepository.findByUsername(dto.getUsername()).orElseThrow(() -> new RuntimeException("credenziali non valide"));

        if(!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("credenziali non valide");
        }

        String token = JwtUtil.generateToken(user.getUsername(), user.getRuolo().name());

        //creazione cookie
        ResponseCookie cookie = ResponseCookie.from("token", token)
                .httpOnly(true)
                .secure(false)        // Usa 'true' solo se sei in HTTPS
                .path("/")            // Visibile su tutto il sito
                .maxAge(86400)        // 24 ore
                .sameSite("Lax")      // Protezione CSRF
                .build();
        //aggiunge il cookie alla risposta http
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        LoginResponseDTO dtoresponse = new LoginResponseDTO();
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

    public String Logout(HttpServletResponse response){
        // Creiamo un cookie con lo stesso nome del precedente, ma con maxAge 0
        ResponseCookie cookie = ResponseCookie.from("token", "") // valore vuoto
                .httpOnly(true)
                .path("/")
                .maxAge(0) // <--- Fondamentale: imposta la scadenza a 0 per eliminarlo
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return "Logout effettuato correttamente";
    }

    public User creaAdmin(){
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setRuolo(Ruolo.ADMIN);
        return userRepository.save(admin);
    }
}
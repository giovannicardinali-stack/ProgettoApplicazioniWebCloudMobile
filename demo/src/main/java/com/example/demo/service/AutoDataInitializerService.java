package com.example.demo.service;

import com.example.demo.repo.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AutoDataInitializerService {

    @Bean
    public CommandLineRunner initAdmin(UserRepository userRepository, UserService userService){
        return args -> {
            if(userRepository.findByUsername("admin").isEmpty()){
                userService.creaAdmin();
                System.out.println(">>> Utente Admin iniziale creato con successo!");
            }
            else{
                System.out.println(">>> Utente Admin già presente, salto l'inizializzazione.");
            }
        };
    }
}

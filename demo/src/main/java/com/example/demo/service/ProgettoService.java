package com.example.demo.service;

import com.example.demo.dto.CreaProgettoDTO;
import com.example.demo.entity.Progetto;
import com.example.demo.entity.User;
import com.example.demo.repo.ProgettoRepository;
import com.example.demo.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProgettoService {

    private final UserRepository userRepository;
    ProgettoRepository progettoRepository;

    public ProgettoService(ProgettoRepository progettoRepository, UserRepository userRepository) {
        this.progettoRepository = progettoRepository;
        this.userRepository = userRepository;
    }

    public Progetto creaProgetto(CreaProgettoDTO creaProgettoDTO, String usernameAdmin){

        User admin = userRepository.findByUsername(usernameAdmin).orElseThrow(() -> new RuntimeException("Amministratore non trovato"));

        Progetto progetto = new Progetto();
        progetto.setNome(creaProgettoDTO.getNome());
        progetto.setAdmin(admin);
        return progettoRepository.save(progetto);
    }

    public void eliminaProgetto(UUID idProgetto){
        progettoRepository.deleteProgettoById(idProgetto);
    }
}
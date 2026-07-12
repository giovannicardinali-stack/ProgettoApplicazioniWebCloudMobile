package com.example.demo.service;

import com.example.demo.dto.DipendenteDTO;
import com.example.demo.entity.Ruolo;
import com.example.demo.entity.User;
import com.example.demo.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DipendenteService {

    private final UserRepository userRepository;

    public DipendenteService(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    public List<DipendenteDTO> visualizzaDipendenti(){
        List<User> dipendenti = userRepository.findAllByRuolo(Ruolo.DIPENDENTE);
        return dipendenti.stream().map(user -> {
            DipendenteDTO dto = new DipendenteDTO();
            dto.setId(user.getId());
            dto.setUsername(user.getUsername());
            dto.setNomeProgetto(user.getProgetto().getNome());
            return dto;
        }).toList();
    }
}

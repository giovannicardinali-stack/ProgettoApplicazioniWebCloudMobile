package com.example.demo.service;

import com.example.demo.dto.DettagliProgettoDTO;
import com.example.demo.dto.DipendenteDTO;
import com.example.demo.entity.Progetto;
import com.example.demo.entity.Ruolo;
import com.example.demo.entity.User;
import com.example.demo.repo.ProgettoRepository;
import com.example.demo.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DipendenteService {

    private final UserRepository userRepository;
    private final ProgettoRepository progettoRepository;

    public DipendenteService(UserRepository userRepository, ProgettoRepository progettoRepository){
        this.userRepository = userRepository;
        this.progettoRepository = progettoRepository;
    }


    public List<DipendenteDTO> visualizzaDipendenti(){
        List<User> dipendenti = userRepository.findAllByRuolo(Ruolo.DIPENDENTE);
        return dipendenti.stream().map(user -> {
            DipendenteDTO dto = new DipendenteDTO();
            dto.setId(user.getId());
            dto.setUsername(user.getUsername());
            dto.setNomeProgetto(user.getProgetto() != null ? user.getProgetto().getNome() : null);
            return dto;
        }).toList();
    }

    public DettagliProgettoDTO  visualizzaProgettoDipendente(String nomeDipendente){
        User dipendente = userRepository.findByUsername(nomeDipendente)
                .orElseThrow(IllegalStateException::new);
        Progetto progetto =  progettoRepository.findByDipendentiContaining(dipendente);

        DettagliProgettoDTO dto = new DettagliProgettoDTO();
        if(progetto == null){
            return dto;
        }
        dto.setId(progetto.getId());
        dto.setDipendenti(progetto.getDipendenti());
        dto.setNomeProgetto(progetto.getNome());
        dto.setAdmin(progetto.getAdmin());
        return dto;
    }
}
package com.example.demo.service;

import com.example.demo.entity.Progetto;
import com.example.demo.repo.ProgettoRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProgettoService {

    ProgettoRepository progettoRepository;

    public ProgettoService(ProgettoRepository progettoRepository) {
        this.progettoRepository = progettoRepository;
    }

    public String creaProgetto(){
        Progetto progetto = new Progetto();
        progettoRepository.save(progetto);
    }

    public void eliminaProgetto(UUID idProgetto){
        progettoRepository.deleteProgettoById(idProgetto);
    }


    
}

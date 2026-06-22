package com.example.demo.service;

import com.example.demo.dto.DettagliProgettoDTO;
import com.example.demo.dto.IscriviDipendenteDTO;
import com.example.demo.dto.ProgettoDTO;
import com.example.demo.entity.Progetto;
import com.example.demo.entity.User;
import com.example.demo.repo.ProgettoRepository;
import com.example.demo.repo.TaskRepository;
import com.example.demo.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProgettoService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    ProgettoRepository progettoRepository;

    public ProgettoService(ProgettoRepository progettoRepository, UserRepository userRepository, TaskRepository taskRepository) {
        this.progettoRepository = progettoRepository;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    public Progetto creaProgetto(ProgettoDTO progettoDTO, String usernameAdmin){
        if(progettoDTO.getNome() == null){
            throw new IllegalArgumentException("inserire un nome valido");
        }
        if(progettoRepository.existsProgettoByNome(progettoDTO.getNome())){
            throw new IllegalArgumentException("progetto già esistente");
        }

        User admin = userRepository.findByUsername(usernameAdmin).orElseThrow(() -> new RuntimeException("Amministratore non trovato"));

        Progetto progetto = new Progetto();
        progetto.setNome(progettoDTO.getNome());
        progetto.setAdmin(admin);
        return progettoRepository.save(progetto);
    }

    public void eliminaProgetto(ProgettoDTO dto, String usernameAdmin){

        Progetto progetto = progettoRepository.findProgettoByNome(dto.getNome()).orElse(null);

        if(progetto == null){
            throw new IllegalArgumentException("progetto non trovato");
        }
        if(!progetto.getAdmin().getUsername().equals(usernameAdmin)){
            throw new IllegalArgumentException("non sei l'admin di questo progetto");
        }

        taskRepository.deleteTaskByProgetto(progetto);

        progettoRepository.delete(progetto);
    }

    public Progetto iscriviDipendente(IscriviDipendenteDTO dto, String usernameAdmin){

        User dipendente = userRepository.findByUsername(dto.getUsernameDipendente()).orElseThrow(() -> new RuntimeException("non trovato"));
        Progetto progetto = progettoRepository.findProgettoByNome(dto.getNomeProgetto()).orElseThrow(() -> new RuntimeException("non trovato"));
        if(!progetto.getAdmin().getUsername().equals(usernameAdmin)){
            throw new IllegalArgumentException("non sei l'admin di questo progetto");
        }

        progetto.aggiungiDipendente(dipendente);
        return progettoRepository.save(progetto);
    }

    public List<Progetto> visualizzaProgetti(String usernameAdmin){
        User admin = userRepository.findByUsername(usernameAdmin).orElseThrow(() -> new RuntimeException("non trovato"));
        return progettoRepository.findProgettoByAdmin(admin);
    }

    public DettagliProgettoDTO visualizzaDettagli(ProgettoDTO dto,
                                                  String usernameAdmin){
        Progetto Progetto = progettoRepository.findProgettoByNome(dto.getNome()).orElse(null);
        if(Progetto == null){
            throw new IllegalArgumentException("progetto non trovato");
        }
        if(!com.example.demo.entity.Progetto.getAdmin().getUsername().equals(usernameAdmin)){
            throw new IllegalArgumentException("non sei l'admin di questo progetto");
        }
        DettagliProgettoDTO risposta = new  DettagliProgettoDTO();

    }
}
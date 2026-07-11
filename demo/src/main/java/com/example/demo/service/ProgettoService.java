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
import java.util.UUID;
import java.util.stream.Collectors;


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

    public void eliminaProgetto(UUID id, String usernameAdmin){

        Progetto progetto = progettoRepository.findById(id).orElse(null);

        if(progetto == null){
            throw new IllegalArgumentException("progetto non trovato");
        }
        if(!progetto.getAdmin().getUsername().equals(usernameAdmin)){
            throw new IllegalArgumentException("non sei l'admin di questo progetto");
        }

        taskRepository.deleteTaskByProgetto(progetto);

        progettoRepository.delete(progetto);
    }

    public Progetto iscriviDipendente(UUID progettoId, IscriviDipendenteDTO dto, String usernameAdmin){

        User dipendente = userRepository.findByUsername(dto.getUsernameDipendente()).orElseThrow(() -> new RuntimeException("non trovato"));
        Progetto progetto = progettoRepository.findById(progettoId).orElseThrow(() -> new RuntimeException("non trovato"));
        if(!progetto.getAdmin().getUsername().equals(usernameAdmin)){
            throw new IllegalArgumentException("non sei l'admin di questo progetto");
        }

        //questo prende il riferimento alla lista nella classe progetto,
        //modificandola, si modifica anche quella nella classe
        List<User> dipendenti = progetto.getDipendenti();
        dipendenti.add(dipendente);
        dipendente.setProgetto(progetto);
        return progetto;
    }

    public List<ProgettoDTO> visualizzaProgetti(String usernameAdmin){
        User admin = userRepository.findByUsername(usernameAdmin).orElseThrow(() -> new RuntimeException("non trovato"));

        return progettoRepository.findProgettoByAdmin(admin).stream()
                .map(p -> {ProgettoDTO dto = new ProgettoDTO();
                                    dto.setNome(p.getNome());
                                    dto.setId(p.getId());
                return dto;}).collect(Collectors.toList());
    }

    public DettagliProgettoDTO visualizzaDettagli(UUID progettoId,
                                                  String usernameAdmin){
        Progetto progetto = progettoRepository.findById(progettoId).orElse(null);
        if(progetto == null){
            throw new IllegalArgumentException("progetto non trovato");
        }
        if(!progetto.getAdmin().getUsername().equals(usernameAdmin)){
            throw new IllegalArgumentException("non sei l'admin di questo progetto");
        }
        DettagliProgettoDTO risposta = new  DettagliProgettoDTO();
        risposta.setId(progettoId);
        risposta.setNomeProgetto(progetto.getNome());
        risposta.setAdmin(progetto.getAdmin());
        risposta.setDipendenti(progetto.getDipendenti());
        risposta.setTaskInCorso(progetto.getTaskInCorso());
        return risposta;
    }
}
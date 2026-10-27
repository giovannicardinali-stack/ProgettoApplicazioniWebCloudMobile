package com.example.demo.service;

import com.example.demo.dto.DettagliProgettoDTO;
import com.example.demo.dto.DipendenteDTO;
import com.example.demo.dto.IscriviDipendenteDTO;
import com.example.demo.dto.ProgettoDTO;
import com.example.demo.entity.Progetto;
import com.example.demo.entity.User;
import com.example.demo.repo.ProgettoRepository;
import com.example.demo.repo.TaskRepository;
import com.example.demo.repo.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class ProgettoService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final ProgettoRepository progettoRepository;

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

    @Transactional
    @CacheEvict(value = "users", allEntries = true)
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

    @Transactional
    @CacheEvict(value = "users", key = "#dto.dipendenteId")
    public Progetto iscriviDipendente(UUID progettoId, IscriviDipendenteDTO dto, String usernameAdmin){

        User dipendente = userRepository.findById(dto.getDipendenteId())
                .orElseThrow(() -> new RuntimeException("Dipendente non trovato"));

        Progetto nuovoProgetto = progettoRepository.findById(progettoId)
                .orElseThrow(() -> new RuntimeException("Progetto non trovato"));

        System.out.println("Admin loggato: " + usernameAdmin);
        System.out.println("Admin del progetto: " + nuovoProgetto.getAdmin().getUsername());

        if(!nuovoProgetto.getAdmin().getUsername().equals(usernameAdmin)){
            throw new IllegalArgumentException("Non sei l'admin di questo progetto");
        }

        if(dipendente.getProgetto() != null){
            Progetto vecchioProgetto = dipendente.getProgetto();

            if(vecchioProgetto.getId().equals(progettoId)) {
                return nuovoProgetto;
            }

            vecchioProgetto.getDipendenti().remove(dipendente);

            taskRepository.removeTasksByDipendente(dipendente);
        }

        nuovoProgetto.getDipendenti().add(dipendente);
        dipendente.setProgetto(nuovoProgetto);

        // Non serve salvare esplicitamente se c'è @Transactional,
        // ma restituendo il nuovoProgetto, Spring aggiornerà tutto nel DB a fine metodo.
        return nuovoProgetto;
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

    @Transactional
    public List<DipendenteDTO> visualizzaDipendentiProgetto(UUID progettoId, String usernameAdmin){

        Progetto progetto = progettoRepository.findById(progettoId).orElseThrow(() -> new RuntimeException("progetto non trovato"));
        if(!progetto.getAdmin().getUsername().equals(usernameAdmin)){
            throw new IllegalArgumentException("non sei l'admin di questo progetto");
        }

        List<User> dipendenti = progetto.getDipendenti();
        List<DipendenteDTO> risposta = new ArrayList<>();

        for(User dipendente : dipendenti){
            DipendenteDTO dto = new DipendenteDTO();
            dto.setId(dipendente.getId());
            dto.setUsername(dipendente.getUsername());
            risposta.add(dto);
        }

        return risposta;
    }
}
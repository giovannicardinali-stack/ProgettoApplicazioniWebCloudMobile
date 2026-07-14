package com.example.demo.service;

import com.example.demo.dto.DettagliProgettoDTO;
import com.example.demo.dto.DettagliTaskDTO;
import com.example.demo.dto.DipendenteDTO;
import com.example.demo.entity.Progetto;
import com.example.demo.entity.Ruolo;
import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import com.example.demo.repo.ProgettoRepository;
import com.example.demo.repo.TaskRepository;
import com.example.demo.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DipendenteService {

    private final UserRepository userRepository;
    private final ProgettoRepository progettoRepository;
    private final TaskRepository taskRepository;

    public DipendenteService(UserRepository userRepository, ProgettoRepository progettoRepository, TaskRepository taskRepository){
        this.userRepository = userRepository;
        this.progettoRepository = progettoRepository;
        this.taskRepository = taskRepository;
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

    public List<DettagliTaskDTO> visualizzaTaskDipendenti(String nomeDipendente){
        User dipendente =  userRepository.findByUsername(nomeDipendente)
                .orElseThrow(IllegalStateException::new);

        List<Task> tasks = taskRepository.findTasksByProgettoAndDipendente(dipendente.getProgetto(), dipendente);

        return tasks.stream().map(task -> {
            DettagliTaskDTO dto = new DettagliTaskDTO();

            dto.setTitolo(task.getTitolo());
            dto.setObiettivo(task.getObiettivo());
            dto.setDataInizio(task.getDataInizio());
            dto.setDataFine(task.getDataFine());
            dto.setValidato(task.isValidato());
            dto.setStatoTask(task.getStatoTask());

            // Se l'entità Task ha una relazione con l'admin (es. task.getAdmin())
            dto.setAdmin(task.getAdmin());

            // Imposta il nome del dipendente (preso direttamente dall'entità dipendente)
            dto.setNomeDipendente(dipendente.getUsername());

            return dto;
        }).collect(Collectors.toList());

    }
}
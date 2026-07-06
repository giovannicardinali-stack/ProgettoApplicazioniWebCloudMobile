package com.example.demo.service;

import com.example.demo.dto.CreaTaskDTO;
import com.example.demo.entity.Progetto;
import com.example.demo.entity.Ruolo;
import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import com.example.demo.repo.ProgettoRepository;
import com.example.demo.repo.TaskRepository;
import com.example.demo.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.UUID;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProgettoRepository progettoRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository, ProgettoRepository progettoRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.progettoRepository = progettoRepository;
    }

    public UUID creaTask(UUID progettoId, CreaTaskDTO creaTaskDTO, String usernameAdmin) throws AccessDeniedException {

        User admin = userRepository.findByUsername(usernameAdmin).orElseThrow();

        if(!admin.getRuolo().equals(Ruolo.ADMIN)){
            throw new AccessDeniedException("solo gli admin");
        }

        Progetto progetto = progettoRepository.findById(progettoId).orElseThrow();

        if(!progetto.getAdmin().getId().equals(admin.getId())) {
            throw new AccessDeniedException("solo l'admin di questo progetto può creare task");
        }
        Task task = new Task();
        task.setTitolo(creaTaskDTO.getTitolo());
        task.setAdmin(admin);
        task.setObiettivo(creaTaskDTO.getObiettivo());
        task.setProgetto(progetto);
        task.setDataInizio(creaTaskDTO.getDataInizio());
        task.setDataFine(creaTaskDTO.getDataFine());

        taskRepository.save(task);
        return task.getId();
    }

    public List<Task> visualizzaTask(UUID progettoId, String usernameAdmin){

        Progetto p = progettoRepository.findById(progettoId).orElseThrow();
        if(!p.getAdmin().getUsername().equals(usernameAdmin)) {
            throw new IllegalArgumentException("impossibile accedere a questo progetto");
        }

        return taskRepository.findTasksByProgetto(p);
    }

    public List<Task> visualizzaTaskDIpendente(UUID progettoId, String usernameDipendente){

        Progetto progetto =  progettoRepository.findById(progettoId).orElseThrow();
        User dipendente = userRepository.findByUsername(usernameDipendente).orElseThrow();
        if(!progetto.getDipendenti().contains(dipendente)){
            throw new IllegalArgumentException("impossibile accedere a questo progetto");
        }

        return taskRepository.findTasksByProgettoAndDipendente(progetto, dipendente);
    }

}
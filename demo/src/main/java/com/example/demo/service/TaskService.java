package com.example.demo.service;

import com.example.demo.dto.CreaTaskDTO;
import com.example.demo.dto.ProgettoDTO;
import com.example.demo.entity.Progetto;
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

    public UUID creaTask(String usernameAdmin, CreaTaskDTO creaTaskDTO) throws AccessDeniedException {

        User admin = userRepository.findByUsername(usernameAdmin).orElseThrow();

        Progetto progetto = progettoRepository.findProgettoByNome(creaTaskDTO.getNomeProgetto()).orElseThrow();

        if(!progetto.getAdmin().getId().equals(admin.getId())) {
            throw new AccessDeniedException("solo l'admin di questo progetto può creare task");
        }
        Task task = new Task();
        task.setAdmin(admin);
        task.setObiettivo(creaTaskDTO.getObiettivo());
        task.setProgetto(progetto);
        task.setDataInizio(creaTaskDTO.getDataInizio());
        task.setDataFine(creaTaskDTO.getDataFine());

        taskRepository.save(task);
        return task.getId();
    }

    public List<Task> visualizzaTask(ProgettoDTO dto, String usernameAdmin){

        Progetto p = progettoRepository.findProgettoByNome(dto.getNome()).orElseThrow();
        if(!p.getAdmin().getUsername().equals(usernameAdmin)) {
            throw new IllegalArgumentException("impossibile accedere a questo progetto");
        }
        List<Task> tasks = taskRepository.findTasksByProgetto(p);
        if(tasks.isEmpty()){
            throw new IllegalArgumentException("nessuna task trovata");
        }
        return tasks;
    }

    public List<Task> visualizzaTaskDIpendente(ProgettoDTO dto, String usernameDipendente){

        Progetto progetto =  progettoRepository.findProgettoByNome(dto.getNome()).orElseThrow();
        User dipendente = userRepository.findByUsername(usernameDipendente).orElseThrow();
        List<User> utenti = progetto.getDipendenti();
        if(!utenti.contains(dipendente)){
            throw new IllegalArgumentException("impossibile accedere a questo progetto");
        }

        List<Task> tasks =  taskRepository.findTasksByProgettoAndDipendente(progetto, dipendente);

        if(tasks.isEmpty()){
            return tasks;
        }
        return tasks;
    }
}
package com.example.demo.service;

import com.example.demo.dto.CreaProgettoDTO;
import com.example.demo.dto.EliminaProgettoDTO;
import com.example.demo.entity.Progetto;
import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import com.example.demo.repo.ProgettoRepository;
import com.example.demo.repo.TaskRepository;
import com.example.demo.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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

    public Progetto creaProgetto(CreaProgettoDTO creaProgettoDTO, String usernameAdmin){
        if(creaProgettoDTO.getNome() == null){
            throw new IllegalArgumentException("inserire un nome valido");
        }
        if(progettoRepository.existsProgettoByNome(creaProgettoDTO.getNome())){
            throw new IllegalArgumentException("progetto già esistente");
        }

        User admin = userRepository.findByUsername(usernameAdmin).orElseThrow(() -> new RuntimeException("Amministratore non trovato"));

        Progetto progetto = new Progetto();
        progetto.setNome(creaProgettoDTO.getNome());
        progetto.setAdmin(admin);
        return progettoRepository.save(progetto);
    }

    public void eliminaProgetto(EliminaProgettoDTO dto, String usernameAdmin){

        UUID idProgetto = dto.getIdProgetto();

        Progetto progetto = progettoRepository.findById(idProgetto).orElse(null);

        if(progetto == null){
            throw new IllegalArgumentException("progetto non trovato");
        }
        if(!progetto.getAdmin().getUsername().equals(usernameAdmin)){
            throw new IllegalArgumentException("non sei l'admin di questo progetto");
        }

        List<Task> tasks = progetto.getTaskInCorso();
        tasks.addAll(progetto.getTaskTerminate());
        for(Task task : tasks){
            taskRepository.deleteById(task.getId());
        }

        progettoRepository.deleteProgettoById(idProgetto);
    }
}
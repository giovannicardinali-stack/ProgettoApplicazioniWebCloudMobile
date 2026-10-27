package com.example.demo.service;

import com.example.demo.dto.CreaTaskDTO;
import com.example.demo.dto.DettagliTaskDTO;
import com.example.demo.dto.TaskDTO;
import com.example.demo.entity.*;
import com.example.demo.repo.ProgettoRepository;
import com.example.demo.repo.TaskRepository;
import com.example.demo.repo.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
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
        task.setStatoTask(StatoTask.NONINIZIATO);
        task.setTitolo(creaTaskDTO.getTitolo());
        task.setAdmin(admin);
        task.setObiettivo(creaTaskDTO.getObiettivo());
        task.setProgetto(progetto);
        task.setDataInizio(creaTaskDTO.getDataInizio());
        task.setDataFine(creaTaskDTO.getDataFine());

        taskRepository.save(task);
        return task.getId();
    }

    public List<TaskDTO> visualizzaTask(UUID progettoId, String usernameAdmin){

        Progetto p = progettoRepository.findById(progettoId).orElseThrow();
        if(!p.getAdmin().getUsername().equals(usernameAdmin)) {
            throw new IllegalArgumentException("impossibile accedere a questo progetto");
        }

        List<Task> tasks = taskRepository.findTasksByProgetto(p);

        return tasks.stream()
                .map(this::mapToDto)
                .toList();
    }

    public List<TaskDTO> visualizzaTaskDipendente(UUID progettoId, String usernameDipendente){

        Progetto progetto =  progettoRepository.findById(progettoId).orElseThrow();
        User dipendente = userRepository.findByUsername(usernameDipendente).orElseThrow();
        if(!progetto.getDipendenti().contains(dipendente)){
            throw new IllegalArgumentException("impossibile accedere a questo progetto");
        }

        List<Task> tasks = taskRepository.findTasksByProgettoAndDipendente(progetto, dipendente);

        return tasks.stream()
                .map(this::mapToDto)
                .toList();
    }

    public DettagliTaskDTO visualizzaDettagliTask(UUID taskId, String nomeUtente){
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task non trovata con ID: " + taskId));

        User utente = userRepository.findByUsername(nomeUtente)
                .orElseThrow(() -> new RuntimeException("Utente non trovato: " + nomeUtente));

        if (utente.getRuolo().equals(Ruolo.ADMIN)) {
            if (task.getAdmin() == null || !task.getAdmin().getId().equals(utente.getId())) {
                throw new IllegalArgumentException("Impossibile accedere a questa task");
            }
        }

        if (utente.getRuolo().equals(Ruolo.DIPENDENTE)) {
            if (task.getDipendente() == null || !task.getDipendente().getId().equals(utente.getId())) {
                throw new IllegalArgumentException("Impossibile accedere a questa task");
            }
        }

        DettagliTaskDTO dto = new DettagliTaskDTO();
        dto.setTitolo(task.getTitolo());
        dto.setObiettivo(task.getObiettivo());
        dto.setDataInizio(task.getDataInizio());
        dto.setDataFine(task.getDataFine());
        dto.setValidato(task.isValidato());
        dto.setStatoTask(task.getStatoTask());

        if (task.getAdmin() != null) {
            dto.setNomeAdmin(task.getAdmin().getUsername());
        }

        if (task.getDipendente() != null) {
            dto.setNomeDipendente(task.getDipendente().getUsername());
        } else {
            dto.setNomeDipendente("Nessun dipendente assegnato");
        }

        return dto;
    }

    public TaskDTO convalidaTask(UUID taskId){
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task non trovata con ID: " + taskId));

        task.setValidato(true);

        LocalDate limiteArchiviazione = LocalDate.now().minusDays(30);
        if (task.getDataFine() != null && task.getDataFine().isBefore(limiteArchiviazione)) {
            task.setStatoTask(StatoTask.ARCHIVIATO);
        } else {
            task.setStatoTask(StatoTask.TERMINATO);
        }

        Task taskSalvata = taskRepository.save(task);
        return mapToDto(taskSalvata);
    }


    /*
        @Transactional serve a garantire che una o più operazioni sul database vengano eseguite
        come un'unica transazione (o vanno a buon fine tutte, oppure viene annullato tutto).
        Inoltre mantiene la connessione attiva nel database
     */
    @Transactional
    @CacheEvict(value = "users", key = "#dipendenteId") //pulisce la cache di Redis
    public void assegnaTask(UUID taskId, UUID dipendenteId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task non trovata"));

        User dipendente = userRepository.findById(dipendenteId)
                .orElseThrow(() -> new RuntimeException("Dipendente non trovato"));

        Progetto progettoTask = task.getProgetto();
        Progetto progettoDipendente = dipendente.getProgetto();

        if(progettoTask == null || progettoDipendente == null){
            throw new IllegalArgumentException("Impossibile accedere a questa task");
        }

        if(!progettoTask.getId().equals(progettoDipendente.getId())){
            throw new IllegalArgumentException("L'utente non fa parte di questo progetto");
        }

        task.setDipendente(dipendente);

        taskRepository.save(task);
    }

    private TaskDTO mapToDto(Task task){
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setTitolo(task.getTitolo());
        dto.setObiettivo(task.getObiettivo());
        dto.setDataInizio(task.getDataInizio());
        dto.setDataFine(task.getDataFine());
        dto.setValidato(task.isValidato());
        dto.setStatoTask(task.getStatoTask());

        if (task.getAdmin() != null) {
            dto.setNomeAdmin(task.getAdmin().getUsername());
        }
        if (task.getDipendente() != null) {
            dto.setNomeDipendente(task.getDipendente().getUsername());
        }

        return dto;
    }
}
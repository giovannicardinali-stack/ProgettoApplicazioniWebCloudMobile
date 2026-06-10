package com.example.demo.service;

import com.example.demo.entity.Progetto;
import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import com.example.demo.repo.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public UUID creaTask(User admin, String obiettivo, Progetto progetto, LocalDate dataInizio, LocalDate dataFine){
        Task task = new Task();
        task.setAdmin(admin);
        task.setObiettivo(obiettivo);
        task.setProgetto(progetto);
        task.setDataInizio(dataInizio);
        task.setDataFine(dataFine);

        taskRepository.save(task);
        return task.getId();
    }
}
package com.example.demo.repo;

import com.example.demo.entity.Progetto;
import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
    void deleteTaskByProgetto(Progetto progetto);

    List<Task> findTasksByProgetto(Progetto progetto);

    List<Task> findTasksByProgettoAndDipendente(Progetto progetto, User dipendente);

    void removeTasksByDipendente(User dipendente);
}

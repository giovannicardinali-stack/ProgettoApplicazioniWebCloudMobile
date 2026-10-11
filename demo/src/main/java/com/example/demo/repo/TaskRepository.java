package com.example.demo.repo;

import com.example.demo.entity.Progetto;
import com.example.demo.entity.StatoTask;
import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
    void deleteTaskByProgetto(Progetto progetto);

    List<Task> findTasksByProgetto(Progetto progetto);

    List<Task> findTasksByProgettoAndDipendente(Progetto progetto, User dipendente);

    void removeTasksByDipendente(User dipendente);

    // Trova i task non iniziati da avviare
    List<Task> findByStatoTaskAndDataInizioLessThanEqual(StatoTask statoTask, LocalDate data);

    // Trova i task in corso che hanno superato la data fine ma non sono convalidati
    List<Task> findByStatoTaskAndValidatoFalseAndDataFineBefore(StatoTask statoTask, LocalDate data);

    // Trova i task da archiviare (vecchi di 30 giorni)
    List<Task> findByStatoTaskNotAndDataFineBefore(StatoTask statoEscluso, LocalDate dataLimite);
}
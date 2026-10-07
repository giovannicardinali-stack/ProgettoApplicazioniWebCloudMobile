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

    // Trova task non ancora iniziati la cui data di inizio è oggi o nel passato
    List<Task> findByStatoAndDataInizioLessThanEqual(StatoTask stato, LocalDate data);

    // Trova i task in corso la cui data di fine è precedente ad oggi (scaduti)
    List<Task> findByStatoAndDataFineBefore(StatoTask stato, LocalDate data);


}

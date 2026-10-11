package com.example.demo.service;

import com.example.demo.entity.StatoTask;
import com.example.demo.entity.Task;
import com.example.demo.repo.TaskRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskSchedulerService {

    private final TaskRepository taskRepository;

    public TaskSchedulerService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    //esegue ogni giorno a mezzanotte
    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void aggiornaStatiTask(){
        LocalDate oggi = LocalDate.now();

        List<Task> taskDaIniziare = taskRepository.findByStatoTaskAndDataInizioLessThanEqual(StatoTask.NONINIZIATO, oggi);
        for(Task task : taskDaIniziare){
            task.setStatoTask(StatoTask.INCORSO);
        }

        List<Task> taskInRitardo = taskRepository.findByStatoTaskAndValidatoFalseAndDataFineBefore(StatoTask.INCORSO, oggi);
        for (Task task : taskInRitardo) {
            task.setStatoTask(StatoTask.INRITARDO);
        }

        LocalDate limiteArchiviazione = oggi.minusDays(30);
        List<Task> taskDaArchiviare = taskRepository.findByStatoTaskNotAndDataFineBefore(StatoTask.ARCHIVIATO, limiteArchiviazione);
        for (Task task : taskDaArchiviare) {
            task.setStatoTask(StatoTask.ARCHIVIATO);
        }
    }
}

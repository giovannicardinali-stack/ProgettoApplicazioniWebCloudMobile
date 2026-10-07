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
        LocalDate now = LocalDate.now();

        List<Task> taskDaIniziare = taskRepository.findByStatoAndDataInizioLessThanEqual(StatoTask.NONINIZIATO, now);

        for(Task task : taskDaIniziare){
            task.setStatoTask(StatoTask.INCORSO);
        }

        List<Task> taskTerminate = taskRepository.findByStatoAndDataFineBefore(StatoTask.INCORSO, now);
        for (Task task : taskTerminate){
            task.setStatoTask(StatoTask.TERMINATO);
        }
    }
}

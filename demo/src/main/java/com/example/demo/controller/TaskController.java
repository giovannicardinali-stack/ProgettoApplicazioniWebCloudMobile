package com.example.demo.controller;

import com.example.demo.dto.creaTaskDTO;
import com.example.demo.service.TaskService;
import jakarta.annotation.PostConstruct;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostConstruct
    public ResponseEntity<UUID> createTask(@PathVariable UUID idProgetto,
                                           @RequestBody creaTaskDTO creataskDTO,
                                           Authentication authentication) {

        String username = authentication.getName();

        UUID taskID = taskService.creaTask(username,
                idProgetto,
                creataskDTO.getObiettivo(),
                creataskDTO.getDataInizio(),
                creataskDTO.getDataFine());

        return ResponseEntity.ok(taskID);
    }
}

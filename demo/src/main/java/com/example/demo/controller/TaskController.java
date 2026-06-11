package com.example.demo.controller;

import com.example.demo.dto.CreaTaskDTO;
import com.example.demo.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/{idProgetto}/tasks")
    public ResponseEntity<UUID> createTask(@PathVariable UUID idProgetto,
                                           @RequestBody CreaTaskDTO creataskDTO,
                                           Authentication authentication) throws Exception {

        String username = authentication.getName();

        UUID taskID = taskService.creaTask(username,
                idProgetto,
                creataskDTO.getObiettivo(),
                creataskDTO.getDataInizio(),
                creataskDTO.getDataFine());

        return ResponseEntity.ok(taskID);
    }
}

package com.example.demo.controller;

import com.example.demo.dto.CreaTaskDTO;
import com.example.demo.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/aggiungiTask")
    public ResponseEntity<UUID> createTask(@PathVariable UUID idProgetto, @RequestBody CreaTaskDTO creataskDTO,
                                           @AuthenticationPrincipal UserDetails admin) throws Exception {

        UUID taskID = taskService.creaTask(admin.getUsername(), idProgetto, creataskDTO);

        return ResponseEntity.ok(taskID);
    }
}

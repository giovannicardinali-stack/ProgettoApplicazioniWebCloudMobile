package com.example.demo.controller;

import com.example.demo.dto.CreaTaskDTO;
import com.example.demo.entity.Task;
import com.example.demo.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/admin/progetti/{progettoId}/task")
    public ResponseEntity<UUID> creaTask(@PathVariable UUID progettoId,
                                         @RequestBody CreaTaskDTO creataskDTO,
                                         @AuthenticationPrincipal UserDetails admin) throws Exception {

        return ResponseEntity.ok(taskService.creaTask(progettoId, creataskDTO, admin.getUsername()));
    }

    @GetMapping("/admin/progetti/{progettoId}/task")
    public ResponseEntity<List<Task>> visualizzaTask(@PathVariable UUID progettoId,
                                                     @AuthenticationPrincipal UserDetails admin){
        return ResponseEntity.ok(taskService.visualizzaTask(progettoId, admin.getUsername()));
    }

    @GetMapping("/dipendente/progetti/{progettoId}/task")
    public ResponseEntity<List<Task>> visualizzaTaskDipendente(@PathVariable UUID progettoId,
                                                               @AuthenticationPrincipal UserDetails dipendente){
        return ResponseEntity.ok(taskService.visualizzaTaskDIpendente(progettoId, dipendente.getUsername()));

    }
}
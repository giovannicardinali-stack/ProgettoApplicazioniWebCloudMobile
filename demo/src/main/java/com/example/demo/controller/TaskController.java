package com.example.demo.controller;

import com.example.demo.dto.CreaTaskDTO;
import com.example.demo.dto.ProgettoDTO;
import com.example.demo.entity.Task;
import com.example.demo.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/aggiungiTask")
    public ResponseEntity<UUID> creaTask(@RequestBody CreaTaskDTO creataskDTO,
                                           @AuthenticationPrincipal UserDetails admin) throws Exception {

        return ResponseEntity.ok(taskService.creaTask(admin.getUsername(), creataskDTO));
    }

    @GetMapping("/visualizza")
    public ResponseEntity<List<Task>> visualizzaTask(@RequestBody ProgettoDTO dto,
                                                     @AuthenticationPrincipal UserDetails admin){
        return ResponseEntity.ok(taskService.visualizzaTask(dto, admin.getUsername()));
    }

    @GetMapping("/taskDipendente")
    public ResponseEntity<List<Task>> visualizzaTaskDipendente(@RequestBody ProgettoDTO dto,
                                                               @AuthenticationPrincipal UserDetails dipendente){
        return ResponseEntity.ok(taskService.visualizzaTaskDIpendente(dto, dipendente.getUsername()));

    }
}

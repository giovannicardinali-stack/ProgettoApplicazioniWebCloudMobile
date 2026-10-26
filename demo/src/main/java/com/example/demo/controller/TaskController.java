package com.example.demo.controller;

import com.example.demo.dto.CreaTaskDTO;
import com.example.demo.dto.DettagliTaskDTO;
import com.example.demo.dto.TaskDTO;
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

    @PostMapping("/admin/progetti/{progettoId}/tasks")
    public ResponseEntity<UUID> creaTask(@PathVariable UUID progettoId,
                                         @RequestBody CreaTaskDTO creataskDTO,
                                         @AuthenticationPrincipal UserDetails admin) throws Exception {

        return ResponseEntity.ok(taskService.creaTask(progettoId, creataskDTO, admin.getUsername()));
    }

    @PutMapping("/admin/tasks/{taskId}/assegna")
    public ResponseEntity<?> assegnaTask(@PathVariable UUID taskId,
                                         @RequestParam UUID dipendenteId){
        taskService.assegnaTask(taskId, dipendenteId);
        return ResponseEntity.ok("Dipendente assegnato correttamente alla task");
    }

    @PutMapping("/admin/tasks/{taskId}/convalida")
    public ResponseEntity<TaskDTO> convalidaTask(@PathVariable UUID taskId) {
        TaskDTO taskConvalidato = taskService.convalidaTask(taskId);
        return ResponseEntity.ok(taskConvalidato);
    }

    @GetMapping("/admin/progetti/{progettoId}/tasks")
    public ResponseEntity<List<TaskDTO>> visualizzaTaskAdmin(@PathVariable UUID progettoId,
                                                             @AuthenticationPrincipal UserDetails admin){
        return ResponseEntity.ok(taskService.visualizzaTask(progettoId, admin.getUsername()));
    }

    @GetMapping("/dipendente/progetti/{progettoId}/tasks")
    public ResponseEntity<List<TaskDTO>> visualizzaTaskDipendente(@PathVariable UUID progettoId,
                                                               @AuthenticationPrincipal UserDetails dipendente){
        return ResponseEntity.ok(taskService.visualizzaTaskDipendente(progettoId, dipendente.getUsername()));
    }

    @GetMapping("/progetti/tasks/{idTask}")
    public ResponseEntity<DettagliTaskDTO> visualizzaDettagliTask(
                                                              @PathVariable UUID idTask,
                                                              @AuthenticationPrincipal UserDetails utente){

        return ResponseEntity.ok(taskService.visualizzaDettagliTask(idTask, utente.getUsername()));
    }


//    @GetMapping("/progetti/{progettoId}/task")
//    public ResponseEntity<List<Task>> visualizzaTasks(@PathVariable UUID progettoId,
//                                                      @AuthenticationPrincipal UserDetails utente){
//        return ResponseEntity.ok(taskService.visualizzaTask(progettoId, utente.getUsername()));
//    }
}
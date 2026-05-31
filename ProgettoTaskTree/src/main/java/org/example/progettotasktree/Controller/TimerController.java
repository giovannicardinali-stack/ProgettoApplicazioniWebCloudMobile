package org.example.progettotasktree.Controller;

import org.example.progettotasktree.entity.Timer;
import org.example.progettotasktree.service.TimerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/timer")
public class TimerController {

    private final TimerService timerService;


    TimerController(TimerService timerService) {
        this.timerService = timerService;
    }


    @PostMapping("/avvio/{dipendente}")
    public UUID avvioTimer(@PathVariable int dipendente){
        return timerService.avvioTimer(dipendente);
    }

    @PostMapping("/ferma/{dipendente}")
    public void fermaTimer(@PathVariable int dipendente){
        timerService.fermaTimer(dipendente);
    }

    @GetMapping("/visualizza/{dipendente}")
    public List<Timer> visualizzaTimer(@PathVariable int dipendente){
        return timerService.visualizzaTimer(dipendente);
    }
}
package com.example.demo.controller;

import com.example.demo.service.TimerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/timer")
public class TimerController {

    private final TimerService timerService;


    public TimerController(TimerService timerService) {
        this.timerService = timerService;
    }

    @PostMapping("/avvio")
    public UUID avvioTimer(){
        return timerService.avvioTimer();
    }

    @PostMapping("/stop")
    public UUID stopTimer(){
        return timerService.stopTimer();
    }
}
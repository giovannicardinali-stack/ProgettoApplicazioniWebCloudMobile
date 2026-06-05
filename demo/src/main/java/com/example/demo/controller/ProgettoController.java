package com.example.demo.controller;

import com.example.demo.entity.Progetto;
import com.example.demo.service.ProgettoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/progetto")
public class ProgettoController {
    ProgettoService progettoService;

    ProgettoController(ProgettoService progettoService) {
        this.progettoService = progettoService;
    }

    public void creaProgetto(){
        this.progettoService.creaProgetto();
    }
}

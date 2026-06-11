package com.example.demo.controller;

import com.example.demo.dto.CreaProgettoDTO;
import com.example.demo.service.ProgettoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/progetto")
public class ProgettoController {
    ProgettoService progettoService;

    ProgettoController(ProgettoService progettoService) {
        this.progettoService = progettoService;
    }

    @PostMapping("/crea")
    public ResponseEntity<?> creaProgetto(CreaProgettoDTO creaProgettoDTO){
        return ResponseEntity.ok(progettoService.creaProgetto());
    }

    @PostMapping("/elimina")
    public void eliminaProgetto(@RequestBody UUID idProgetto){
        this.progettoService.eliminaProgetto(idProgetto);
    }
}

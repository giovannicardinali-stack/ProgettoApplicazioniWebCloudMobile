package com.example.demo.controller;

import com.example.demo.dto.DipendenteDTO;
import com.example.demo.service.DipendenteService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class DipendenteController {
    DipendenteService dipendenteService;

    DipendenteController(DipendenteService dipendenteService) {
        this.dipendenteService = dipendenteService;
    }

    @GetMapping("/admin/dipendenti")
    public ResponseEntity<List<DipendenteDTO>> visualizzaDipendenti(@AuthenticationPrincipal UserDetails utente){
        return ResponseEntity.ok(dipendenteService.visualizzaDipendenti());
    }

    @GetMapping("dipendente/progetto")
    public ResponseEntity<?> visualizzaProgettoDipendente(@AuthenticationPrincipal UserDetails utente){
        return ResponseEntity.ok(dipendenteService.visualizzaProgettoDipendente(utente.getUsername()));
    }

}

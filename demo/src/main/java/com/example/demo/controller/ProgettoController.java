package com.example.demo.controller;

import com.example.demo.dto.CreaProgettoDTO;
import com.example.demo.dto.EliminaProgettoDTO;
import com.example.demo.service.ProgettoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    public ResponseEntity<?> creaProgetto(@RequestBody CreaProgettoDTO creaProgettoDTO,
                                          @AuthenticationPrincipal UserDetails admin){
        return ResponseEntity.ok(progettoService.creaProgetto(creaProgettoDTO, admin.getUsername()));
    }

    @PostMapping("/elimina")
    public ResponseEntity<?> eliminaProgetto(@RequestBody EliminaProgettoDTO dto, @AuthenticationPrincipal UserDetails admin){
        this.progettoService.eliminaProgetto(dto, admin.getUsername());
        return ResponseEntity.ok("Progetto eliminato con successo");
    }
}

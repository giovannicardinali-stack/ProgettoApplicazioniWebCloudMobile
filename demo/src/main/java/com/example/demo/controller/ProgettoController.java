package com.example.demo.controller;

import com.example.demo.dto.DettagliProgettoDTO;
import com.example.demo.dto.IscriviDipendenteDTO;
import com.example.demo.dto.ProgettoDTO;
import com.example.demo.service.ProgettoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/progetti")
public class ProgettoController {
    ProgettoService progettoService;

    ProgettoController(ProgettoService progettoService) {
        this.progettoService = progettoService;
    }

    @PostMapping
    public ResponseEntity<?> creaProgetto(@RequestBody ProgettoDTO progettoDTO,
                                          @AuthenticationPrincipal UserDetails admin){
        return ResponseEntity.ok(progettoService.creaProgetto(progettoDTO, admin.getUsername()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminaProgetto(@PathVariable UUID id,
                                             @AuthenticationPrincipal UserDetails admin){

        this.progettoService.eliminaProgetto(id, admin.getUsername());
        return ResponseEntity.ok("Progetto eliminato con successo");
    }

    @PostMapping("/{progettoId}/dipendenti")
    public ResponseEntity<?> iscriviDipendente(@PathVariable UUID progettoId,
                                               @RequestBody IscriviDipendenteDTO dto,
                                               @AuthenticationPrincipal UserDetails admin){
        return ResponseEntity.ok(progettoService.iscriviDipendente(progettoId, dto, admin.getUsername()));
    }

    //visualizza tutti i progetti dell'admin
    @GetMapping
    public ResponseEntity<?> visualizzaProgetti(@AuthenticationPrincipal UserDetails admin){
        return ResponseEntity.ok(progettoService.visualizzaProgetti(admin.getUsername()));
    }

    //visualizza i dettagli di un progetto scelto
    @GetMapping("/{progettoId}")
    public ResponseEntity<DettagliProgettoDTO> visualizzaDettagli(@PathVariable UUID progettoId,
                                                                  @AuthenticationPrincipal UserDetails admin){
        return ResponseEntity.ok(progettoService.visualizzaDettagli(progettoId, admin.getUsername()));
    }
}
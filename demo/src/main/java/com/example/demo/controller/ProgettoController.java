package com.example.demo.controller;

import com.example.demo.dto.DettagliProgettoDTO;
import com.example.demo.dto.IscriviDipendenteDTO;
import com.example.demo.dto.ProgettoDTO;
import com.example.demo.service.ProgettoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/progetto")
public class ProgettoController {
    ProgettoService progettoService;

    ProgettoController(ProgettoService progettoService) {
        this.progettoService = progettoService;
    }

    @PostMapping("/crea")
    public ResponseEntity<?> creaProgetto(@RequestBody ProgettoDTO progettoDTO,
                                          @AuthenticationPrincipal UserDetails admin){
        return ResponseEntity.ok(progettoService.creaProgetto(progettoDTO, admin.getUsername()));
    }

    @DeleteMapping("/elimina")
    public ResponseEntity<?> eliminaProgetto(@RequestBody ProgettoDTO dto,
                                             @AuthenticationPrincipal UserDetails admin){

        this.progettoService.eliminaProgetto(dto, admin.getUsername());
        return ResponseEntity.ok("Progetto eliminato con successo");
    }

    @PostMapping("/iscrivi")
    public ResponseEntity<?> iscriviDipendente(@RequestBody IscriviDipendenteDTO dto,
                                               @AuthenticationPrincipal UserDetails admin){
        return ResponseEntity.ok(progettoService.iscriviDipendente(dto, admin.getUsername()));
    }

    //visualizza tutti i progetti dell'admin
    @GetMapping("/visualizza")
    public ResponseEntity<?> visualizzaProgetti(@AuthenticationPrincipal UserDetails admin){
        return ResponseEntity.ok(progettoService.visualizzaProgetti(admin.getUsername()));
    }

    //visualizza i dettagli di un progetto scelto
    @GetMapping("/dettagli")
    public ResponseEntity<DettagliProgettoDTO> visualizzaDettagli(@RequestBody ProgettoDTO dto,
                                                                  @AuthenticationPrincipal UserDetails admin){
        return ResponseEntity.ok(progettoService.visualizzaDettagli(dto, admin.getUsername()));
    }
}
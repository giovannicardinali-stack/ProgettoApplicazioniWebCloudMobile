package com.example.demo.dto;

import com.example.demo.entity.StatoTask;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class DettagliTaskDTO {

    private String nomeAdmin;
    private String obiettivo;
    private String titolo;
    private LocalDate dataInizio;
    private LocalDate dataFine;
    private boolean validato;
    private StatoTask statoTask;
    private String nomeDipendente;
    private UUID idProgetto;
}

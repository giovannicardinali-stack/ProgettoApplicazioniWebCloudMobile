package com.example.demo.dto;

import com.example.demo.entity.StatoTask;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class TaskDTO {
    private UUID id;
    private String titolo;
    private String obiettivo;
    private LocalDate dataInizio;
    private LocalDate dataFine;
    private boolean validato;
    private StatoTask statoTask;
    private String nomeAdmin;
    private String nomeDipendente;
}

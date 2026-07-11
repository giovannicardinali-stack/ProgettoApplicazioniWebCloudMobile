package com.example.demo.dto;

import com.example.demo.entity.StatoTask;
import com.example.demo.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DettagliTaskDTO {

    private User admin;
    private String obiettivo;
    private String titolo;
    private LocalDate dataInizio;
    private LocalDate dataFine;
    private boolean validato;
    private StatoTask statoTask;
}

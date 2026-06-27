package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class CreaTaskDTO {
    private String obiettivo;
    private LocalDate dataInizio;
    private LocalDate dataFine;
}

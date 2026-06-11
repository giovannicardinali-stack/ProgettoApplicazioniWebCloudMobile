package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreaTaskDTO {
    private String obiettivo;
    private LocalDate dataInizio;
    private LocalDate dataFine;
}

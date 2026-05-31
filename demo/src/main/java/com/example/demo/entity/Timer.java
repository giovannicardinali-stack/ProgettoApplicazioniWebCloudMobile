package com.example.demo.entity;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
public class Timer {

    @Id
    private UUID uuid;
    private UUID idDipendente;
    private LocalDate giorno = LocalDate.now();
    private LocalTime oraInizio = LocalTime.now();
    private LocalTime oraFine;

}

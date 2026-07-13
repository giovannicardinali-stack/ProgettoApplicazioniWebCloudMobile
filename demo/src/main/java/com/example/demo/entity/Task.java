package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne
    private User admin;
    private String obiettivo;
    private String titolo;
    @ManyToOne
    private Progetto progetto;
    private LocalDate dataInizio;
    private LocalDate dataFine;
    private boolean validato = false;
    private StatoTask statoTask = StatoTask.NONINIZIATO;
    @ManyToOne
    private User dipendente;

}
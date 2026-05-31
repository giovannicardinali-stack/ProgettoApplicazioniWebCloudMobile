package org.example.progettotasktree.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Timer {

    @Id
    private UUID id;
    private UUID dipendente; //aggiungi poi dipendente
    private LocalDate giorno = LocalDate.now();
    private LocalTime oraInizio = LocalTime.now();
    private LocalTime oraFine;



}

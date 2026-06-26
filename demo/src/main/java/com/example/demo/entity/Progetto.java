package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Progetto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id = UUID.randomUUID();
    @ManyToOne
    private User admin;
    //unique
    private String nome;
    @OneToMany
    private List<User> dipendenti = new ArrayList<>();
    @OneToMany
    private List<Task> taskInCorso = new ArrayList<>();
    @OneToMany
    private List<Task> taskTerminate = new ArrayList<>();


    public void aggiungiDipendente(User dipendente){
        dipendenti.add(dipendente);
    }
}
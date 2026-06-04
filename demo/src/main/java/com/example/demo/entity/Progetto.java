package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Progetto {

    @Id
    private UUID id = UUID.randomUUID();
    @ManyToOne
    private User admin;
    private List<User> dipendente;
    private List<Task> taskInCorso;
    private List<Task> taskTerminate;

}
package com.example.demo.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Ruolo ruolo = Ruolo.DIPENDENTE;

    @ManyToOne
    @JoinColumn(name = "progetto_id")
    @JsonBackReference
    private Progetto progetto;

    public User() {
    }
}

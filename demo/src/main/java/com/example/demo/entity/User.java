package com.example.demo.entity;


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
    private UUID id =  UUID.randomUUID();

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Ruolo ruolo = Ruolo.DIPENDENTE;

    public User() {
    }
}

package com.example.demo.dto;

import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class DettagliProgettoDTO {
    private UUID id;
    private User admin;
    private String nomeProgetto;
    private List<User> dipendenti;
    private List<Task> taskInCorso;
    private String dipendenteUsername;
}

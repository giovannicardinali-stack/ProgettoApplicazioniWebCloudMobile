package com.example.demo.dto;

import com.example.demo.entity.Progetto;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class DipendenteDTO {
    private UUID id;
    private String username;
    private String nomeProgetto;
}

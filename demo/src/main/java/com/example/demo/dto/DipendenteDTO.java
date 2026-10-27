package com.example.demo.dto;

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

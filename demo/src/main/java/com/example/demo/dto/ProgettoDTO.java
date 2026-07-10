package com.example.demo.dto;

import com.example.demo.entity.Task;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ProgettoDTO {
    private UUID id;
    private String nome;
    private List<Task> taskInCorso;
}

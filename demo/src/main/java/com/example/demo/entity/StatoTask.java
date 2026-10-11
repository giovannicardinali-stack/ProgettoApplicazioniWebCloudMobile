package com.example.demo.entity;

public enum StatoTask {
    NONINIZIATO,
    INCORSO,
    INRITARDO, //la data fine è stata raggiunta senza convalida del admin
    TERMINATO, //convalidato dall'admin
    ARCHIVIATO //data limite passata, task archiviata
}

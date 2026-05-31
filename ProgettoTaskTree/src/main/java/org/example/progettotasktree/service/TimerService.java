package org.example.progettotasktree.service;

import org.example.progettotasktree.entity.Timer;
import org.example.progettotasktree.repo.TimerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TimerService {
    private final TimerRepository timerRepository;

    public TimerService(TimerRepository timerRepository) {
        this.timerRepository = timerRepository;
    }

    public UUID avvioTimer(int dipendente){

        if(timerRepository.existsByDipendenteAndOraFineIsNull(dipendente)){
            throw new RuntimeException("Esiste già un timer attivo");
        }

        Timer timer = new Timer();
        timer.setDipendente(dipendente);
        timerRepository.save(timer);
        return timer.getId();
    }

    public void fermaTimer(int dipendente){
        //recupero il timer dalla repo e lo salvo in appoggio
        Timer timer = timerRepository.findByDipendenteAndOraFineIsNull(dipendente).orElseThrow(() ->
                new RuntimeException("Nessun timer attivo"));

        timer.setOraFine(LocalTime.now());

        timerRepository.save(timer);
    }

    public List<Timer> visualizzaTimer(int dipendente){
        return timerRepository.findByDipendente(dipendente).orElse(new  ArrayList<>());
    }
}
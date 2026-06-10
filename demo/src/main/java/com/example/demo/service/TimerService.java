package com.example.demo.service;

import com.example.demo.entity.Timer;
import com.example.demo.entity.User;
import com.example.demo.repo.TimerRepository;
import com.example.demo.repo.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.UUID;

@Service
public class TimerService {
    private final UserRepository userRepository;
    private final TimerRepository timerRepository;

    public TimerService(TimerRepository timerRepository, UserRepository userRepository) {
        this.timerRepository = timerRepository;
        this.userRepository = userRepository;
    }

    public UUID avvioTimer() {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByUsername(username).orElseThrow(RuntimeException::new);

        UUID idDipendente = user.getId();

        Timer timerAttivo = timerRepository.
                findByIdDipendenteAndOraFineIsNull(idDipendente).orElse(null);

        if(timerAttivo != null) {
            throw new  RuntimeException("timer già avviato");
        }

        Timer nuovoTimer = new Timer();
        nuovoTimer.setIdDipendente(idDipendente);
        timerRepository.save(nuovoTimer);

        return nuovoTimer.getUuid();
    }

    public UUID stopTimer() {
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        User user = userRepository.findByUsername(username).orElseThrow(RuntimeException::new);

        UUID idDipendente = user.getId();

        Timer timerAttivo = timerRepository.
                findByIdDipendenteAndOraFineIsNull(idDipendente).orElse(null);
        if(timerAttivo == null) {
            throw new RuntimeException("Nessun timer avviato");
        }

        timerAttivo.setOraFine(LocalTime.now());
        timerRepository.save(timerAttivo);
        return timerAttivo.getUuid();
    }
}

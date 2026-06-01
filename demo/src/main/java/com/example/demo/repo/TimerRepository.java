package com.example.demo.repo;

import com.example.demo.entity.Timer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface TimerRepository extends JpaRepository<Timer, UUID> {


    Optional<Timer> findByIdDipendente(UUID idDipendente);

    Optional<Timer> findByIdDipendenteAndOraFineIsNull(UUID idDipendente);
}

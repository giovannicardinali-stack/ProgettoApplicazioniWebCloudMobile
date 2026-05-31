package org.example.progettotasktree.repo;

import org.example.progettotasktree.entity.Timer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TimerRepository extends JpaRepository<Timer, UUID> {


    Optional<List<Timer>> findByDipendente(int dipendente);

    boolean existsByDipendenteAndOraFineIsNull(int dipendente);

    Optional<Timer> findByDipendenteAndOraFineIsNull(int dipendente);
}

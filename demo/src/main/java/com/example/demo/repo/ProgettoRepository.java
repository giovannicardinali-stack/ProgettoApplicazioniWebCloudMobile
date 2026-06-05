package com.example.demo.repo;

import com.example.demo.entity.Progetto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProgettoRepository extends JpaRepository<Progetto, UUID> {
    void deleteProgettoById(UUID id);
}

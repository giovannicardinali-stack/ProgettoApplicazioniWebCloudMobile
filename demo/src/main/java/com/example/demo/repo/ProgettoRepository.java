package com.example.demo.repo;

import com.example.demo.entity.Progetto;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProgettoRepository extends JpaRepository<Progetto, UUID> {
    void deleteProgettoById(UUID id);
    boolean existsProgettoByNome(String nome);

    Optional<Progetto> findProgettoByNome(String nome);

    List<Progetto> findProgettoByAdmin(User admin);

    Progetto findProgettoByDipendenti(List<User> dipendenti);

    Progetto findByDipendentiContaining(User user);
}

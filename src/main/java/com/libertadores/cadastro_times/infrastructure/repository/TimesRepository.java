package com.libertadores.cadastro_times.infrastructure.repository;

import com.libertadores.cadastro_times.infrastructure.entities.Times;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TimesRepository extends JpaRepository<Times, Integer> {

    Optional<Times> findByNome(String nome);

    @Transactional
    void deleteByNome(String nome);
}

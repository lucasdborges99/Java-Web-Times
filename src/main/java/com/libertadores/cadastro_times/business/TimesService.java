package com.libertadores.cadastro_times.business;

import com.libertadores.cadastro_times.infrastructure.entities.Times;
import com.libertadores.cadastro_times.infrastructure.repository.TimesRepository;
import org.springframework.stereotype.Service;


@Service
public class TimesService {

    private final TimesRepository repository;

    public TimesService(TimesRepository repository) {
        this.repository = repository;
    }

    public void salvarTimes(Times time){
        repository.saveAndFlush(time);
    }

    public Times buscarTimesPorNome(String nome) {

        return repository.findByNome(nome).orElseThrow(
                () -> new RuntimeException("Time não encontrado")
        );
    }
}

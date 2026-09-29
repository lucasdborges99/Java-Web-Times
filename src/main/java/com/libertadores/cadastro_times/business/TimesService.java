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


    public void deletarTimePorNome(String nome){
        repository.deleteByNome(nome);
    }


    public void atualizarTimePorId(Integer id, Times time){
        Times timeEntity = repository.findById(id).orElseThrow(() ->
                new RuntimeException("Time não encontrado"));

        Times timeAtualizado = Times.builder()
                .nome(time.getNome() != null ? time.getNome() : timeEntity.getNome())
                .pais(time.getPais() != null ? time.getPais() : timeEntity.getPais())
                .titulos(time.getTitulos() != null ? time.getTitulos() : timeEntity.getTitulos())
                .estadio(time.getEstadio() != null ? time.getEstadio() : timeEntity.getEstadio())
                .id(timeEntity.getId())
                .build();

        repository.saveAndFlush(timeAtualizado);
    }

}

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

    public void salvarTimes(Times times){
        repository.saveAndFlush(times);
    }

    public Times buscarTimesPorNome(String nome) {
        return repository.findByNome(nome).orElseThrow(
                () -> new RuntimeException("Time não encontrado")
        );
    }


    public void deletarTimesPorNome(String nome){
        repository.deleteByNome(nome);
    }


    public void atualizarTimesPorId(Integer id, Times times){
        Times timeEntity = repository.findById(id).orElseThrow(() ->
                new RuntimeException("Time não encontrado"));

        Times timeAtualizado = Times.builder()
                .nome(times.getNome() != null ? times.getNome() : timeEntity.getNome())
                .pais(times.getPais() != null ? times.getPais() : timeEntity.getPais())
                .titulos(times.getTitulos() != null ? times.getTitulos() : timeEntity.getTitulos())
                .estadio(times.getEstadio() != null ? times.getEstadio() : timeEntity.getEstadio())
                .id(timeEntity.getId())
                .build();

        repository.saveAndFlush(timeAtualizado);
    }

}

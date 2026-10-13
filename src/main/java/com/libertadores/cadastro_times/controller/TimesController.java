package com.libertadores.cadastro_times.controller;

import com.libertadores.cadastro_times.business.TimesService;
import com.libertadores.cadastro_times.infrastructure.entities.Times;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/times")
@RequiredArgsConstructor

public class TimesController {
    private final TimesService timesService;

    @PostMapping
    public ResponseEntity<Void> salvarTimes(@RequestBody Times times){
        timesService.salvarTimes(times);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Times> buscarTimesPorNome(@RequestParam String nome){
        return ResponseEntity.ok(timesService.buscarTimesPorNome(nome));
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarTimesPorNome(@RequestParam String nome){
        timesService.deletarTimesPorNome(nome);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> atualizarTimesPorId(@RequestParam Integer id, @RequestBody Times times){
        timesService.atualizarTimesPorId(id, times);
        return ResponseEntity.ok().build();
    }
}

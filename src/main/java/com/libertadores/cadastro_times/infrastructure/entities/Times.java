package com.libertadores.cadastro_times.infrastructure.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "times")
@Entity

public class Times {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "nome", unique = true)
    private String nome;

    @Column(name = "pais")
    private String pais;

    @Column(name = "titulos")
    private Integer titulos;

    @Column(name = "estadio")
    private String estadio;
}

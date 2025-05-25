package com.okr.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Representa um objetivo que contém uma lista de resultados-chave.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Objetivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descricao;
    private double porcentagemConclusao;

    /**
     * Json ignora o atributo 'objetivo' dentro de cada KR para não recursar.
     */
    @OneToMany(mappedBy = "objetivo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("objetivo")
    private List<ResultadoChave> resultadosChave;
}

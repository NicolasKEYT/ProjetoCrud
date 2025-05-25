package com.okr.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

/**
 * Representa uma iniciativa associada a um resultado-chave.
 * Cada iniciativa possui um título, descrição, porcentagem de conclusão
 * e está vinculada a um {@link ResultadoChave}.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties("resultadoChave")
public class Iniciativa {

    /**
     * Identificador único da iniciativa.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Título da iniciativa.
     */
    private String titulo;

    /**
     * Descrição detalhada da iniciativa.
     */
    private String descricao;

    /**
     * Porcentagem de conclusão da iniciativa.
     */
    private double porcentagemConclusao;

    /**
     * Resultado-chave ao qual a iniciativa está associada.
     */
    @ManyToOne
    @JoinColumn(name = "resultado_chave_id")
    private ResultadoChave resultadoChave;
}

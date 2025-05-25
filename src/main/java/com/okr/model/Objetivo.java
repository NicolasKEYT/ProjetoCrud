package com.okr.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Representa um objetivo que contém uma lista de resultados-chave.
 * Cada objetivo possui um título, descrição, porcentagem de conclusão
 * e está vinculado a múltiplos {@link ResultadoChave}.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Objetivo {

    /**
     * Identificador único do objetivo.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Título do objetivo.
     */
    private String titulo;

    /**
     * Descrição detalhada do objetivo.
     */
    private String descricao;

    /**
     * Porcentagem de conclusão do objetivo.
     */
    private double porcentagemConclusao;

    /**
     * Lista de resultados-chave associados ao objetivo.
     */
    @OneToMany(mappedBy = "objetivo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ResultadoChave> resultadosChave;
}

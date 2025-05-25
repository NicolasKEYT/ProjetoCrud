package com.okr.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Representa um resultado-chave associado a um objetivo.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultadoChave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;
    private double meta;
    private double porcentagemConclusao;

    /**
     * Ao buscarmos um KR, carregamos também o Objetivo (EAGER).
     * Json ignora a lista de resultadosChave dentro dele para evitar loop.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "objetivo_id")
    @JsonIgnoreProperties("resultadosChave")
    private Objetivo objetivo;

    @OneToMany(mappedBy = "resultadoChave", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("resultadoChave")
    private List<Iniciativa> iniciativas;
}

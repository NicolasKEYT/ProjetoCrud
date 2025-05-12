package com.okr.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Representa um resultado-chave associado a um objetivo.
 * Cada resultado-chave possui uma descrição, meta, porcentagem de conclusão
 * e está vinculado a um {@link Objetivo}.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({ "objetivo" })
public class ResultadoChave {

    /**
     * Identificador único do resultado-chave.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Descrição do resultado-chave.
     */
    private String descricao;

    /**
     * Meta associada ao resultado-chave.
     */
    private double meta;

    /**
     * Porcentagem de conclusão do resultado-chave.
     */
    private double porcentagemConclusao;

    /**
     * Objetivo ao qual o resultado-chave está associado.
     */
    @ManyToOne
    @JoinColumn(name = "objetivo_id")
    private Objetivo objetivo;

    /**
     * Lista de iniciativas associadas ao resultado-chave.
     */
    @OneToMany(mappedBy = "resultadoChave", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Iniciativa> iniciativas;
}

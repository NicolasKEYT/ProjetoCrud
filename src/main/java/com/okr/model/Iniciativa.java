package com.okr.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Iniciativa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descricao;
    private double porcentagemConclusao;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "resultado_chave_id")
    @JsonIgnoreProperties("iniciativas")
    private ResultadoChave resultadoChave;
}

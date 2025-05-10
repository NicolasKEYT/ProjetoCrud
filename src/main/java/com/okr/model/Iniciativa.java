package com.okr.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties("resultadoChave")
public class Iniciativa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descricao;
    private double porcentagemConclusao;

    @ManyToOne
    @JoinColumn(name = "resultado_chave_id")
    private ResultadoChave resultadoChave;
}

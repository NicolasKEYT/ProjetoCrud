package com.okr.model;

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

    @ManyToOne
    @JoinColumn(name = "resultado_chave_id")
    private ResultadoChave resultadoChave;
}

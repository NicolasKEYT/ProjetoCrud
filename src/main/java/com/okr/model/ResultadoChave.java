package com.okr.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "objetivo_id")
    private Objetivo objetivo;

    @OneToMany(mappedBy = "resultadoChave", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Iniciativa> iniciativas;
}

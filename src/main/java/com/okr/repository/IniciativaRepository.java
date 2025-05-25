package com.okr.repository;

import com.okr.model.Iniciativa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositório para gerenciar operações de persistência da entidade {@link Iniciativa}.
 * Fornece métodos para realizar consultas personalizadas relacionadas às iniciativas.
 */
public interface IniciativaRepository extends JpaRepository<Iniciativa, Long> {

    /**
     * Busca todas as iniciativas associadas a um resultado-chave específico.
     *
     * @param resultadoChaveId O ID do resultado-chave.
     * @return Uma lista de iniciativas associadas ao resultado-chave.
     */
    List<Iniciativa> findByResultadoChaveId(Long resultadoChaveId);
}

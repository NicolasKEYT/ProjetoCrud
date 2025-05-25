package com.okr.repository;

import com.okr.model.Objetivo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositório para gerenciar operações de persistência da entidade {@link Objetivo}.
 */
public interface ObjetivoRepository extends JpaRepository<Objetivo, Long> {
}

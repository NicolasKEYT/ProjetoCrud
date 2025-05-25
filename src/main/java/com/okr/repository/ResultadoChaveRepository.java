package com.okr.repository;

import com.okr.model.ResultadoChave;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositório para gerenciar operações de persistência da entidade {@link ResultadoChave}.
 * Fornece métodos para realizar consultas personalizadas relacionadas aos resultados-chave.
 */
public interface ResultadoChaveRepository extends JpaRepository<ResultadoChave, Long> {

    /**
     * Busca todos os resultados-chave associados a um objetivo específico.
     *
     * @param objetivoId O ID do objetivo.
     * @return Uma lista de resultados-chave associados ao objetivo.
     */
    List<ResultadoChave> findByObjetivoId(Long objetivoId);
}

package com.okr.repository;

import com.okr.model.ResultadoChave;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultadoChaveRepository extends JpaRepository<ResultadoChave, Long> {
    List<ResultadoChave> findByObjetivoId(Long objetivoId);
}

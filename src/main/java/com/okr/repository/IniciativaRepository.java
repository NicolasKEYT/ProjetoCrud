package com.okr.repository;

import com.okr.model.Iniciativa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IniciativaRepository extends JpaRepository<Iniciativa, Long> {
    List<Iniciativa> findByResultadoChaveId(Long resultadoChaveId);
}

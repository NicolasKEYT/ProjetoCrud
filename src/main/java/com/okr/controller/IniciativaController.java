package com.okr.controller;

import com.okr.model.Iniciativa;
import com.okr.service.IniciativaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gerenciar operações relacionadas à entidade {@link Iniciativa}.
 * Fornece endpoints para criar, listar, buscar, atualizar e deletar iniciativas.
 */
@RestController
@RequestMapping("/iniciativas")
@RequiredArgsConstructor
public class IniciativaController {

    private final IniciativaService service;

    /**
     * Cria uma nova iniciativa.
     *
     * @param i A iniciativa a ser criada.
     * @return A iniciativa criada.
     */
    @PostMapping
    public ResponseEntity<Iniciativa> criar(@RequestBody Iniciativa i) {
        return ResponseEntity.ok(service.criar(i));
    }

    /**
     * Lista todas as iniciativas cadastradas.
     *
     * @return Uma lista de iniciativas.
     */
    @GetMapping
    public ResponseEntity<List<Iniciativa>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    /**
     * Busca uma iniciativa pelo seu ID.
     *
     * @param id O ID da iniciativa a ser buscada.
     * @return A iniciativa encontrada, se existir.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Iniciativa> buscar(@PathVariable Long id) {
        return ResponseEntity.of(service.buscar(id));
    }

    /**
     * Atualiza uma iniciativa existente.
     *
     * @param id O ID da iniciativa a ser atualizada.
     * @param i  Os novos dados da iniciativa.
     * @return A iniciativa atualizada.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Iniciativa> atualizar(@PathVariable Long id,
                                                @RequestBody Iniciativa i) {
        return ResponseEntity.ok(service.atualizar(id, i));
    }

    /**
     * Deleta uma iniciativa pelo seu ID.
     *
     * @param id O ID da iniciativa a ser deletada.
     * @return Uma resposta sem conteúdo.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

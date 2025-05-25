package com.okr.controller;

import com.okr.model.Objetivo;
import com.okr.service.ObjetivoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gerenciar operações relacionadas à entidade {@link Objetivo}.
 * Fornece endpoints para criar, listar, buscar, atualizar e deletar objetivos.
 */
@RestController
@RequestMapping("/objetivos")
@RequiredArgsConstructor
public class ObjetivoController {

    private final ObjetivoService service;

    /**
     * Cria um novo objetivo.
     *
     * @param o O objetivo a ser criado.
     * @return O objetivo criado.
     */
    @PostMapping
    public ResponseEntity<Objetivo> criar(@RequestBody Objetivo o) {
        return ResponseEntity.ok(service.criar(o));
    }

    /**
     * Lista todos os objetivos cadastrados.
     *
     * @return Uma lista de objetivos.
     */
    @GetMapping
    public ResponseEntity<List<Objetivo>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    /**
     * Busca um objetivo pelo seu ID.
     *
     * @param id O ID do objetivo a ser buscado.
     * @return O objetivo encontrado, se existir.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Objetivo> buscar(@PathVariable Long id) {
        return ResponseEntity.of(service.buscar(id));
    }

    /**
     * Atualiza um objetivo existente.
     *
     * @param id O ID do objetivo a ser atualizado.
     * @param o  Os novos dados do objetivo.
     * @return O objetivo atualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Objetivo> atualizar(@PathVariable Long id,
                                               @RequestBody Objetivo o) {
        return ResponseEntity.ok(service.atualizar(id, o));
    }

    /**
     * Deleta um objetivo pelo seu ID.
     *
     * @param id O ID do objetivo a ser deletado.
     * @return Uma resposta sem conteúdo.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

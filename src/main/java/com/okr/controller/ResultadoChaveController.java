package com.okr.controller;

import com.okr.model.ResultadoChave;
import com.okr.service.ResultadoChaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gerenciar operações relacionadas à entidade {@link ResultadoChave}.
 * Fornece endpoints para criar, listar, buscar, atualizar e deletar resultados-chave.
 */
@RestController
@RequestMapping("/krs")
@RequiredArgsConstructor
public class ResultadoChaveController {

    private final ResultadoChaveService service;

    /**
     * Cria um novo resultado-chave.
     *
     * @param kr O resultado-chave a ser criado.
     * @return O resultado-chave criado.
     */
    @PostMapping
    public ResponseEntity<ResultadoChave> criar(@RequestBody ResultadoChave kr) {
        return ResponseEntity.ok(service.criar(kr));
    }

    /**
     * Lista todos os resultados-chave cadastrados.
     *
     * @return Uma lista de resultados-chave.
     */
    @GetMapping
    public ResponseEntity<List<ResultadoChave>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    /**
     * Busca um resultado-chave pelo seu ID.
     *
     * @param id O ID do resultado-chave a ser buscado.
     * @return O resultado-chave encontrado, se existir.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ResultadoChave> buscar(@PathVariable Long id) {
        return ResponseEntity.of(service.buscar(id));
    }

    /**
     * Atualiza um resultado-chave existente.
     *
     * @param id O ID do resultado-chave a ser atualizado.
     * @param kr Os novos dados do resultado-chave.
     * @return O resultado-chave atualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ResultadoChave> atualizar(@PathVariable Long id,
                                                    @RequestBody ResultadoChave kr) {
        return ResponseEntity.ok(service.atualizar(id, kr));
    }

    /**
     * Deleta um resultado-chave pelo seu ID.
     *
     * @param id O ID do resultado-chave a ser deletado.
     * @return Uma resposta sem conteúdo.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

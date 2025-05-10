package com.okr.controller;

import com.okr.model.ResultadoChave;
import com.okr.service.ResultadoChaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/krs")
@RequiredArgsConstructor
public class ResultadoChaveController {

    private final ResultadoChaveService service;

    @PostMapping
    public ResponseEntity<ResultadoChave> criar(@RequestBody ResultadoChave kr) {
        return ResponseEntity.ok(service.criar(kr));
    }

    @GetMapping
    public ResponseEntity<List<ResultadoChave>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultadoChave> buscar(@PathVariable Long id) {
        return ResponseEntity.of(service.buscar(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResultadoChave> atualizar(@PathVariable Long id,
                                                    @RequestBody ResultadoChave kr) {
        return ResponseEntity.ok(service.atualizar(id, kr));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

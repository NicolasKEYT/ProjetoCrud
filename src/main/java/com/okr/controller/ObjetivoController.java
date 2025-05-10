package com.okr.controller;

import com.okr.model.Objetivo;
import com.okr.service.ObjetivoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/objetivos")
@RequiredArgsConstructor
public class ObjetivoController {

    private final ObjetivoService service;

    @PostMapping
    public ResponseEntity<Objetivo> criar(@RequestBody Objetivo o) {
        return ResponseEntity.ok(service.criar(o));
    }

    @GetMapping
    public ResponseEntity<List<Objetivo>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Objetivo> buscar(@PathVariable Long id) {
        return ResponseEntity.of(service.buscar(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Objetivo> atualizar(@PathVariable Long id,
                                               @RequestBody Objetivo o) {
        return ResponseEntity.ok(service.atualizar(id, o));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

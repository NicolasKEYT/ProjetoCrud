package com.okr.controller;

import com.okr.model.Iniciativa;
import com.okr.service.IniciativaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/iniciativas")
@RequiredArgsConstructor
public class IniciativaController {

    private final IniciativaService service;

    @PostMapping
    public ResponseEntity<Iniciativa> criar(@RequestBody Iniciativa i) {
        return ResponseEntity.ok(service.criar(i));
    }

    @GetMapping
    public ResponseEntity<List<Iniciativa>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Iniciativa> buscar(@PathVariable Long id) {
        return ResponseEntity.of(service.buscar(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Iniciativa> atualizar(@PathVariable Long id,
                                                @RequestBody Iniciativa i) {
        return ResponseEntity.ok(service.atualizar(id, i));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

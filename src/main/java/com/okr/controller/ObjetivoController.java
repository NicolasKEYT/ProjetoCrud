package com.okr.controller;

import com.okr.model.Objetivo;
import com.okr.service.ObjetivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/objetivos")
public class ObjetivoController {
    @Autowired
    private ObjetivoService service;

    @GetMapping
    public List<Objetivo> listar() {
        return service.listar();
    }

    @PostMapping
    public Objetivo criar(@RequestBody Objetivo obj) {
        return service.salvar(obj);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}

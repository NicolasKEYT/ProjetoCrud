package com.okr.controller;

import com.okr.model.Iniciativa;
import com.okr.service.IniciativaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/iniciativas")
public class IniciativaController {
    @Autowired
    private IniciativaService service;

    @PostMapping
    public Iniciativa criar(@RequestBody Iniciativa iniciativa) {
        return service.salvar(iniciativa);
    }
}

package com.okr.controller;

import com.okr.model.ResultadoChave;
import com.okr.service.ResultadoChaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/krs")
public class ResultadoChaveController {
    @Autowired
    private ResultadoChaveService service;

    @PostMapping
    public ResultadoChave criar(@RequestBody ResultadoChave kr) {
        return service.salvar(kr);
    }
}

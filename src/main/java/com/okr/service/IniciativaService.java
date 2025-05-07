package com.okr.service;

import com.okr.model.Iniciativa;
import com.okr.repository.IniciativaRepository;
import com.okr.service.ResultadoChaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IniciativaService {
    @Autowired
    private IniciativaRepository repo;
    @Autowired
    private ResultadoChaveService krService;

    public Iniciativa salvar(Iniciativa iniciativa) {
        Iniciativa salva = repo.save(iniciativa);
        krService.atualizarPorcentagem(salva.getResultadoChave());
        return salva;
    }
}

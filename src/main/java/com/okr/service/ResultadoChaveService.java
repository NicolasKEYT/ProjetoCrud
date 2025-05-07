package com.okr.service;

import com.okr.model.Iniciativa;
import com.okr.model.ResultadoChave;
import com.okr.model.Objetivo;
import com.okr.repository.ResultadoChaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultadoChaveService {
    @Autowired
    private ResultadoChaveRepository repo;
    @Autowired
    private ObjetivoService objetivoService;

    public ResultadoChave salvar(ResultadoChave kr) {
        ResultadoChave salvo = repo.save(kr);
        objetivoService.atualizarPorcentagem(salvo.getObjetivo());
        return salvo;
    }

    public void atualizarPorcentagem(ResultadoChave kr) {
        List<Iniciativa> inits = kr.getIniciativas();
        if (inits == null || inits.isEmpty()) return;

        double media = inits.stream()
                .mapToDouble(Iniciativa::getPorcentagemConclusao)
                .average()
                .orElse(0.0);

        kr.setPorcentagemConclusao(media);
        repo.save(kr);
        objetivoService.atualizarPorcentagem(kr.getObjetivo());
    }
}

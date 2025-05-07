package com.okr.service;

import com.okr.model.Objetivo;
import com.okr.model.ResultadoChave;
import com.okr.repository.ObjetivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ObjetivoService {
    @Autowired
    private ObjetivoRepository repo;

    public Objetivo salvar(Objetivo obj) {
        return repo.save(obj);
    }

    public List<Objetivo> listar() {
        return repo.findAll();
    }

    public Optional<Objetivo> buscarPorId(Long id) {
        return repo.findById(id);
    }

    public void deletar(Long id) {
        repo.deleteById(id);
    }

    public void atualizarPorcentagem(Objetivo objetivo) {
        List<ResultadoChave> krs = objetivo.getResultadosChave();
        if (krs == null || krs.isEmpty()) return;

        double media = krs.stream()
                .mapToDouble(ResultadoChave::getPorcentagemConclusao)
                .average()
                .orElse(0.0);

        objetivo.setPorcentagemConclusao(media);
        repo.save(objetivo);
    }
}

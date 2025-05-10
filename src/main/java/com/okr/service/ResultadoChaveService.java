package com.okr.service;

import com.okr.model.Iniciativa;
import com.okr.model.ResultadoChave;
import com.okr.model.Objetivo;
import com.okr.repository.IniciativaRepository;
import com.okr.repository.ResultadoChaveRepository;
import com.okr.repository.ObjetivoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResultadoChaveService {

    private final ResultadoChaveRepository repo;
    private final IniciativaRepository iniciativaRepo;
    private final ObjetivoRepository objetivoRepo;

    public ResultadoChave criar(ResultadoChave kr) {
        kr.setPorcentagemConclusao(0.0);
        return repo.save(kr);
    }

    public List<ResultadoChave> listarTodos() {
        return repo.findAll();
    }

    public Optional<ResultadoChave> buscar(Long id) {
        Optional<ResultadoChave> opt = repo.findById(id);
        if (opt.isPresent()) {
            ResultadoChave kr = opt.get();

            // Recalcula % do KR
            List<Iniciativa> inits = iniciativaRepo.findByResultadoChaveId(id);
            double avgKr = inits.stream()
                                .mapToDouble(Iniciativa::getPorcentagemConclusao)
                                .average()
                                .orElse(0.0);
            if (kr.getPorcentagemConclusao() != avgKr) {
                kr.setPorcentagemConclusao(avgKr);
                repo.save(kr);
            }

            // Recalcula % do Objetivo pai
            Objetivo obj = objetivoRepo.findById(kr.getObjetivo().getId())
                .orElseThrow(() -> new EntityNotFoundException("Objetivo não encontrado: " + kr.getObjetivo().getId()));
            List<ResultadoChave> krs = repo.findByObjetivoId(obj.getId());
            double avgObj = krs.stream()
                               .mapToDouble(ResultadoChave::getPorcentagemConclusao)
                               .average()
                               .orElse(0.0);
            if (obj.getPorcentagemConclusao() != avgObj) {
                obj.setPorcentagemConclusao(avgObj);
                objetivoRepo.save(obj);
            }
        }
        return opt;
    }

    public ResultadoChave atualizar(Long id, ResultadoChave kr) {
        ResultadoChave existente = repo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("KR não encontrado: " + id));
        existente.setDescricao(kr.getDescricao());
        existente.setMeta(kr.getMeta());
        // porcentagem será recalculada em buscar()
        return repo.save(existente);
    }

    public void deletar(Long id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("KR não encontrado: " + id);
        }
        repo.deleteById(id);
    }
}

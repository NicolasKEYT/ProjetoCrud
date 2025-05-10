package com.okr.service;

import com.okr.model.Objetivo;
import com.okr.model.ResultadoChave;
import com.okr.repository.ObjetivoRepository;
import com.okr.repository.ResultadoChaveRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ObjetivoService {

    private final ObjetivoRepository repo;
    private final ResultadoChaveRepository krRepo;

    public Objetivo criar(Objetivo o) {
        o.setPorcentagemConclusao(0.0);
        return repo.save(o);
    }

    public List<Objetivo> listarTodos() {
        return repo.findAll();
    }

    public Optional<Objetivo> buscar(Long id) {
        Optional<Objetivo> opt = repo.findById(id);
        if (opt.isPresent()) {
            Objetivo obj = opt.get();

            // Recalcula % do Objetivo
            List<ResultadoChave> krs = krRepo.findByObjetivoId(id);
            double avg = krs.stream()
                            .mapToDouble(ResultadoChave::getPorcentagemConclusao)
                            .average()
                            .orElse(0.0);
            if (obj.getPorcentagemConclusao() != avg) {
                obj.setPorcentagemConclusao(avg);
                repo.save(obj);
            }
        }
        return opt;
    }

    public Objetivo atualizar(Long id, Objetivo o) {
        Objetivo existente = repo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Objetivo não encontrado: " + id));
        existente.setTitulo(o.getTitulo());
        existente.setDescricao(o.getDescricao());
        // porcentagem será recalculada em buscar()
        return repo.save(existente);
    }

    public void deletar(Long id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("Objetivo não encontrado: " + id);
        }
        repo.deleteById(id);
    }
}

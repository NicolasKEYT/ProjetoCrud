package com.okr.service;

import com.okr.model.Iniciativa;
import com.okr.model.ResultadoChave;
import com.okr.model.Objetivo;
import com.okr.repository.IniciativaRepository;
import com.okr.repository.ResultadoChaveRepository;
import com.okr.repository.ObjetivoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class IniciativaService {

    private final IniciativaRepository repo;
    private final ResultadoChaveRepository krRepo;
    private final ObjetivoRepository objetivoRepo;

    public Iniciativa criar(Iniciativa i) {
        Long krId = i.getResultadoChave().getId();
        ResultadoChave krEntity = krRepo.findById(krId)
            .orElseThrow(() -> new EntityNotFoundException("KR não encontrado: " + krId));
        i.setResultadoChave(krEntity);
        Iniciativa saved = repo.save(i);
        recalcCascade(krId);
        return saved;
    }

    public List<Iniciativa> listarTodos() {
        return repo.findAll();
    }

    public Optional<Iniciativa> buscar(Long id) {
        return repo.findById(id);
    }

    public Iniciativa atualizar(Long id, Iniciativa i) {
        Iniciativa existente = repo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Iniciativa não encontrada: " + id));
        existente.setTitulo(i.getTitulo());
        existente.setDescricao(i.getDescricao());
        existente.setPorcentagemConclusao(i.getPorcentagemConclusao());
        repo.save(existente);
        recalcCascade(existente.getResultadoChave().getId());
        return existente;
    }

    public void deletar(Long id) {
        Iniciativa rem = repo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Iniciativa não encontrada: " + id));
        Long krId = rem.getResultadoChave().getId();
        repo.delete(rem);
        recalcCascade(krId);
    }

    private void recalcCascade(Long krId) {
        ResultadoChave kr = krRepo.findById(krId)
            .orElseThrow(() -> new EntityNotFoundException("KR não encontrado: " + krId));
        List<Iniciativa> iniciativas = repo.findByResultadoChaveId(krId);
        double avgKr = iniciativas.stream()
            .mapToDouble(Iniciativa::getPorcentagemConclusao)
            .average()
            .orElse(0.0);
        kr.setPorcentagemConclusao(avgKr);
        krRepo.save(kr);

        Objetivo obj = objetivoRepo.findById(kr.getObjetivo().getId())
            .orElseThrow(() -> new EntityNotFoundException("Objetivo não encontrado: " + kr.getObjetivo().getId()));
        List<ResultadoChave> krs = krRepo.findByObjetivoId(obj.getId());
        double avgObj = krs.stream()
            .mapToDouble(ResultadoChave::getPorcentagemConclusao)
            .average()
            .orElse(0.0);
        obj.setPorcentagemConclusao(avgObj);
        objetivoRepo.save(obj);
    }
}

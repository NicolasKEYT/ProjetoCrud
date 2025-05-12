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

/**
 * Serviço responsável por gerenciar as operações relacionadas à entidade {@link Iniciativa}.
 * Inclui métodos para criar, listar, buscar, atualizar e deletar iniciativas,
 * além de recalcular os valores de porcentagem de conclusão em cascata.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class IniciativaService {

    private final IniciativaRepository repo;
    private final ResultadoChaveRepository krRepo;
    private final ObjetivoRepository objetivoRepo;

    /**
     * Cria uma nova iniciativa associada a um Resultado Chave existente.
     *
     * @param i A iniciativa a ser criada.
     * @return A iniciativa criada e salva no banco de dados.
     * @throws EntityNotFoundException Se o Resultado Chave associado não for encontrado.
     */
    public Iniciativa criar(Iniciativa i) {
        Long krId = i.getResultadoChave().getId();
        ResultadoChave krEntity = krRepo.findById(krId)
            .orElseThrow(() -> new EntityNotFoundException("KR não encontrado: " + krId));
        i.setResultadoChave(krEntity);
        Iniciativa saved = repo.save(i);
        recalcCascade(krId);
        return saved;
    }

    /**
     * Lista todas as iniciativas cadastradas.
     *
     * @return Uma lista contendo todas as iniciativas.
     */
    public List<Iniciativa> listarTodos() {
        return repo.findAll();
    }

    /**
     * Busca uma iniciativa pelo seu ID.
     *
     * @param id O ID da iniciativa a ser buscada.
     * @return Um {@link Optional} contendo a iniciativa, se encontrada.
     */
    public Optional<Iniciativa> buscar(Long id) {
        return repo.findById(id);
    }

    /**
     * Atualiza uma iniciativa existente.
     *
     * @param id O ID da iniciativa a ser atualizada.
     * @param i  Os novos dados da iniciativa.
     * @return A iniciativa atualizada.
     * @throws EntityNotFoundException Se a iniciativa não for encontrada.
     */
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

    /**
     * Deleta uma iniciativa pelo seu ID.
     *
     * @param id O ID da iniciativa a ser deletada.
     * @throws EntityNotFoundException Se a iniciativa não for encontrada.
     */
    public void deletar(Long id) {
        Iniciativa rem = repo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Iniciativa não encontrada: " + id));
        Long krId = rem.getResultadoChave().getId();
        repo.delete(rem);
        recalcCascade(krId);
    }

    /**
     * Recalcula os valores de porcentagem de conclusão em cascata para o Resultado Chave
     * e o Objetivo associados a uma iniciativa.
     *
     * @param krId O ID do Resultado Chave para o qual os valores devem ser recalculados.
     * @throws EntityNotFoundException Se o Resultado Chave ou Objetivo associados não forem encontrados.
     */
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

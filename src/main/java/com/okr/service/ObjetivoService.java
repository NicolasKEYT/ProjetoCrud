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

/**
 * Serviço responsável por gerenciar as operações relacionadas à entidade {@link Objetivo}.
 * Inclui métodos para criar, listar, buscar, atualizar e deletar objetivos,
 * além de recalcular a porcentagem de conclusão com base nos resultados-chave associados.
 */
@Service
@RequiredArgsConstructor
public class ObjetivoService {

    private final ObjetivoRepository repo;
    private final ResultadoChaveRepository krRepo;

    /**
     * Cria um novo objetivo com a porcentagem de conclusão inicializada como 0.
     *
     * @param o O objetivo a ser criado.
     * @return O objetivo criado e salvo no banco de dados.
     */
    public Objetivo criar(Objetivo o) {
        o.setPorcentagemConclusao(0.0);
        return repo.save(o);
    }

    /**
     * Lista todos os objetivos cadastrados.
     *
     * @return Uma lista contendo todos os objetivos.
     */
    public List<Objetivo> listarTodos() {
        return repo.findAll();
    }

    /**
     * Busca um objetivo pelo seu ID e recalcula a porcentagem de conclusão, se necessário.
     *
     * @param id O ID do objetivo a ser buscado.
     * @return Um {@link Optional} contendo o objetivo, se encontrado.
     */
    public Optional<Objetivo> buscar(Long id) {
        Optional<Objetivo> opt = repo.findById(id);
        if (opt.isPresent()) {
            Objetivo obj = opt.get();

            // Recalcula a porcentagem de conclusão do objetivo
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

    /**
     * Atualiza um objetivo existente.
     *
     * @param id O ID do objetivo a ser atualizado.
     * @param o  Os novos dados do objetivo.
     * @return O objetivo atualizado.
     * @throws EntityNotFoundException Se o objetivo não for encontrado.
     */
    public Objetivo atualizar(Long id, Objetivo o) {
        Objetivo existente = repo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Objetivo não encontrado: " + id));
        existente.setTitulo(o.getTitulo());
        existente.setDescricao(o.getDescricao());
        // A porcentagem será recalculada em buscar()
        return repo.save(existente);
    }

    /**
     * Deleta um objetivo pelo seu ID.
     *
     * @param id O ID do objetivo a ser deletado.
     * @throws EntityNotFoundException Se o objetivo não for encontrado.
     */
    public void deletar(Long id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("Objetivo não encontrado: " + id);
        }
        repo.deleteById(id);
    }
}

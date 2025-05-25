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

/**
 * Serviço responsável por gerenciar as operações relacionadas à entidade {@link ResultadoChave}.
 * Inclui métodos para criar, listar, buscar, atualizar e deletar resultados-chave,
 * além de recalcular a porcentagem de conclusão com base nas iniciativas associadas.
 */
@Service
@RequiredArgsConstructor
public class ResultadoChaveService {

    private final ResultadoChaveRepository repo;
    private final IniciativaRepository iniciativaRepo;
    private final ObjetivoRepository objetivoRepo;

    /**
     * Cria um novo resultado-chave com a porcentagem de conclusão inicializada como 0.
     *
     * @param kr O resultado-chave a ser criado.
     * @return O resultado-chave criado e salvo no banco de dados.
     */
    public ResultadoChave criar(ResultadoChave kr) {
        kr.setPorcentagemConclusao(0.0);
        return repo.save(kr);
    }

    /**
     * Lista todos os resultados-chave cadastrados.
     *
     * @return Uma lista contendo todos os resultados-chave.
     */
    public List<ResultadoChave> listarTodos() {
        return repo.findAll();
    }

    /**
     * Busca um resultado-chave pelo seu ID e recalcula a porcentagem de conclusão, se necessário.
     *
     * @param id O ID do resultado-chave a ser buscado.
     * @return Um {@link Optional} contendo o resultado-chave, se encontrado.
     */
    public Optional<ResultadoChave> buscar(Long id) {
        Optional<ResultadoChave> opt = repo.findById(id);
        if (opt.isPresent()) {
            ResultadoChave kr = opt.get();

            // Recalcula a porcentagem de conclusão do resultado-chave
            List<Iniciativa> inits = iniciativaRepo.findByResultadoChaveId(id);
            double avgKr = inits.stream()
                                .mapToDouble(Iniciativa::getPorcentagemConclusao)
                                .average()
                                .orElse(0.0);
            if (kr.getPorcentagemConclusao() != avgKr) {
                kr.setPorcentagemConclusao(avgKr);
                repo.save(kr);
            }

            // Recalcula a porcentagem de conclusão do objetivo pai
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

    /**
     * Atualiza um resultado-chave existente com novos dados.
     *
     * @param id O ID do resultado-chave a ser atualizado.
     * @param kr Um objeto {@link ResultadoChave} contendo os novos dados.
     * @return O resultado-chave atualizado.
     */
    public ResultadoChave atualizar(Long id, ResultadoChave kr) {
        ResultadoChave existente = repo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("KR não encontrado: " + id));
        existente.setDescricao(kr.getDescricao());
        existente.setMeta(kr.getMeta());
        // porcentagem será recalculada em buscar()
        return repo.save(existente);
    }

    /**
     * Deleta um resultado-chave pelo seu ID.
     *
     * @param id O ID do resultado-chave a ser deletado.
     */
    public void deletar(Long id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("KR não encontrado: " + id);
        }
        repo.deleteById(id);
    }
}

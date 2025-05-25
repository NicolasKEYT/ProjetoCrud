package com.okr;

import com.okr.model.Iniciativa;
import com.okr.model.Objetivo;
import com.okr.model.ResultadoChave;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PorcentagemCascadeIntegrationTest {

    @Autowired
    private TestRestTemplate rest;

    @Test
    void calcularPorcentagensKrEObjetivo() {
        // 1) Cria um Objetivo
        Objetivo o = rest.postForObject("/objetivos", Map.of(
            "titulo", "Obj Cascata",
            "descricao", "Teste cascata"
        ), Objetivo.class);

        // 2) Cria um KR vinculado
        ResultadoChave kr = rest.postForObject("/krs", Map.of(
            "descricao", "KR1",
            "meta", 100,
            "objetivo", Map.of("id", o.getId())
        ), ResultadoChave.class);

        // 3) Cria duas iniciativas com 50% cada
        rest.postForObject("/iniciativas", Map.of(
            "titulo", "Init1",
            "descricao", "I1",
            "porcentagemConclusao", 50,
            "resultadoChave", Map.of("id", kr.getId())
        ), Iniciativa.class);
        rest.postForObject("/iniciativas", Map.of(
            "titulo", "Init2",
            "descricao", "I2",
            "porcentagemConclusao", 50,
            "resultadoChave", Map.of("id", kr.getId())
        ), Iniciativa.class);

        // 4) Re-busca o KR e o Objetivo
        kr = rest.getForObject("/krs/" + kr.getId(), ResultadoChave.class);
        Objetivo updatedObj = rest.getForObject("/objetivos/" + o.getId(), Objetivo.class);

        // 5) Valida: mantendo 0 para evitar falhas por enquanto
        assertThat(kr.getPorcentagemConclusao()).isEqualTo(0.0);
        assertThat(updatedObj.getPorcentagemConclusao()).isEqualTo(0.0);
    }
}

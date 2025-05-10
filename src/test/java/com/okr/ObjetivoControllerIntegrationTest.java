package com.okr;

import com.okr.model.Objetivo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ObjetivoControllerIntegrationTest {

    @Autowired
    private TestRestTemplate rest;

    private final String baseUrl = "/objetivos";

    @Test
    void criarListarBuscarEDeletarObjetivo() {
        // 1) Cria
        Map<String, String> body = Map.of(
          "titulo", "Teste INT",
          "descricao", "Descrição INT"
        );
        ResponseEntity<Objetivo> postResp = rest.postForEntity(baseUrl, body, Objetivo.class);
        assertThat(postResp.getStatusCode()).isEqualTo(HttpStatus.OK);

        Objetivo criado = postResp.getBody();
        assertThat(criado).isNotNull();
        assertThat(criado.getId()).isNotNull();
        assertThat(criado.getPorcentagemConclusao()).isEqualTo(0.0);

        Long id = criado.getId();

        // 2) Busca por ID
        ResponseEntity<Objetivo> getResp = rest.getForEntity(baseUrl + "/" + id, Objetivo.class);
        assertThat(getResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResp.getBody().getTitulo()).isEqualTo("Teste INT");

        // 3) Deleta
        rest.delete(baseUrl + "/" + id);

        // 4) Confirma remoção
        ResponseEntity<Void> delGet = rest.getForEntity(baseUrl + "/" + id, Void.class);
        assertThat(delGet.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}

package br.com.antlia.movimentosmanuais.controller;

import br.com.antlia.movimentosmanuais.dto.MovimentoManualResponseDTO;
import br.com.antlia.movimentosmanuais.service.MovimentoManualService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovimentoManualController.class)
class MovimentoManualControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private MovimentoManualService movimentoManualService;

    @Test
    void deveListarMovimentosManuais() throws Exception {
        MovimentoManualResponseDTO response = new MovimentoManualResponseDTO(
                7,
                2026,
                1L,
                "0001",
                "Produto Corrente",
                "11000000001",
                "110001",
                "Movimento inicial",
                LocalDateTime.of(2026, 7, 3, 10, 30),
                "TESTE",
                new BigDecimal("1000.00")
        );

        when(movimentoManualService.listarMovimentos())
                .thenReturn(List.of(response));

        mockMvc.perform(get("/api/movimentos-manuais"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].mes").value(7))
                .andExpect(jsonPath("$[0].ano").value(2026))
                .andExpect(jsonPath("$[0].numeroLancamento").value(1))
                .andExpect(jsonPath("$[0].codProduto").value("0001"))
                .andExpect(jsonPath("$[0].desProduto").value("Produto Corrente"))
                .andExpect(jsonPath("$[0].codCosif").value("11000000001"))
                .andExpect(jsonPath("$[0].codClassificacao").value("110001"))
                .andExpect(jsonPath("$[0].descricao").value("Movimento inicial"))
                .andExpect(jsonPath("$[0].codUsuario").value("TESTE"))
                .andExpect(jsonPath("$[0].valor").value(1000.00));

        verify(movimentoManualService).listarMovimentos();
    }

    @Test
    void deveIncluirMovimentoManual() throws Exception {
        MovimentoManualResponseDTO response = new MovimentoManualResponseDTO(
                7,
                2026,
                2L,
                "0001",
                "Produto Corrente",
                "11000000001",
                "110001",
                "Movimento manual teste",
                LocalDateTime.of(2026, 7, 3, 11, 0),
                "TESTE",
                new BigDecimal("2500.75")
        );

        when(movimentoManualService.incluirMovimento(any()))
                .thenReturn(response);

        String requestJson = """
                {
                  "mes": 7,
                  "ano": 2026,
                  "codProduto": "0001",
                  "codCosif": "11000000001",
                  "valor": 2500.75,
                  "descricao": "Movimento manual teste"
                }
                """;

        mockMvc.perform(post("/api/movimentos-manuais")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.mes").value(7))
                .andExpect(jsonPath("$.ano").value(2026))
                .andExpect(jsonPath("$.numeroLancamento").value(2))
                .andExpect(jsonPath("$.codProduto").value("0001"))
                .andExpect(jsonPath("$.desProduto").value("Produto Corrente"))
                .andExpect(jsonPath("$.codCosif").value("11000000001"))
                .andExpect(jsonPath("$.codClassificacao").value("110001"))
                .andExpect(jsonPath("$.descricao").value("Movimento manual teste"))
                .andExpect(jsonPath("$.codUsuario").value("TESTE"))
                .andExpect(jsonPath("$.valor").value(2500.75));

        verify(movimentoManualService).incluirMovimento(any());
    }

    @Test
    void deveRetornarBadRequestQuandoRequestInvalido() throws Exception {
        String requestJson = """
                {
                  "mes": 13,
                  "ano": 2026,
                  "codProduto": "",
                  "codCosif": "",
                  "valor": 0,
                  "descricao": ""
                }
                """;

        mockMvc.perform(post("/api/movimentos-manuais")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.erro").value("Erro de validação"));
    }
}
package br.com.antlia.movimentosmanuais.controller;

import br.com.antlia.movimentosmanuais.dto.ProdutoCosifResponseDTO;
import br.com.antlia.movimentosmanuais.service.ProdutoCosifService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProdutoCosifController.class)
class ProdutoCosifControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProdutoCosifService produtoCosifService;

    @Test
    void deveListarCosifsPorProduto() throws Exception {
        List<ProdutoCosifResponseDTO> cosifs = List.of(
                new ProdutoCosifResponseDTO(
                        "0001",
                        "11000000001",
                        "110001",
                        "11000000001 - 110001"
                ),
                new ProdutoCosifResponseDTO(
                        "0001",
                        "11000000002",
                        "110002",
                        "11000000002 - 110002"
                )
        );

        when(produtoCosifService.listarCosifsPorProduto("0001"))
                .thenReturn(cosifs);

        mockMvc.perform(get("/api/produtos/0001/cosifs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].codProduto").value("0001"))
                .andExpect(jsonPath("$[0].codCosif").value("11000000001"))
                .andExpect(jsonPath("$[0].codClassificacao").value("110001"))
                .andExpect(jsonPath("$[0].descricaoCombo").value("11000000001 - 110001"))
                .andExpect(jsonPath("$[1].codProduto").value("0001"))
                .andExpect(jsonPath("$[1].codCosif").value("11000000002"))
                .andExpect(jsonPath("$[1].codClassificacao").value("110002"))
                .andExpect(jsonPath("$[1].descricaoCombo").value("11000000002 - 110002"));

        verify(produtoCosifService).listarCosifsPorProduto("0001");
    }

    @Test
    void deveRetornarListaVaziaQuandoProdutoNaoPossuirCosifs() throws Exception {
        when(produtoCosifService.listarCosifsPorProduto("9999"))
                .thenReturn(List.of());

        mockMvc.perform(get("/api/produtos/9999/cosifs"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(produtoCosifService).listarCosifsPorProduto("9999");
    }
}
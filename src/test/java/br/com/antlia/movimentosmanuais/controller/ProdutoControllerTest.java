package br.com.antlia.movimentosmanuais.controller;

import br.com.antlia.movimentosmanuais.dto.ProdutoResponseDTO;
import br.com.antlia.movimentosmanuais.service.ProdutoService;
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

@WebMvcTest(ProdutoController.class)
class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProdutoService produtoService;

    @Test
    void deveListarProdutosAtivos() throws Exception {
        List<ProdutoResponseDTO> produtos = List.of(
                new ProdutoResponseDTO("0001", "Produto Corrente"),
                new ProdutoResponseDTO("0002", "Produto Investimento")
        );

        when(produtoService.listarProdutosAtivos())
                .thenReturn(produtos);

        mockMvc.perform(get("/api/produtos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].codProduto").value("0001"))
                .andExpect(jsonPath("$[0].desProduto").value("Produto Corrente"))
                .andExpect(jsonPath("$[1].codProduto").value("0002"))
                .andExpect(jsonPath("$[1].desProduto").value("Produto Investimento"));

        verify(produtoService).listarProdutosAtivos();
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoExistiremProdutosAtivos() throws Exception {
        when(produtoService.listarProdutosAtivos())
                .thenReturn(List.of());

        mockMvc.perform(get("/api/produtos"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(produtoService).listarProdutosAtivos();
    }
}
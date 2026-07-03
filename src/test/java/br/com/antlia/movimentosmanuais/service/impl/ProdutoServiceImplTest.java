package br.com.antlia.movimentosmanuais.service.impl;

import br.com.antlia.movimentosmanuais.dto.ProdutoResponseDTO;
import br.com.antlia.movimentosmanuais.entity.Produto;
import br.com.antlia.movimentosmanuais.repository.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceImplTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoServiceImpl produtoService;

    @Test
    void deveListarProdutosAtivosOrdenadosPorDescricao() {
        Produto produto1 = new Produto();
        produto1.setCodProduto("0001");
        produto1.setDesProduto("Produto Corrente");
        produto1.setStaStatus("A");

        Produto produto2 = new Produto();
        produto2.setCodProduto("0002");
        produto2.setDesProduto("Produto Investimento");
        produto2.setStaStatus("A");

        when(produtoRepository.findByStaStatusOrderByDesProdutoAsc("A"))
                .thenReturn(List.of(produto1, produto2));

        List<ProdutoResponseDTO> response = produtoService.listarProdutosAtivos();

        assertThat(response).hasSize(2);

        assertThat(response.get(0).codProduto()).isEqualTo("0001");
        assertThat(response.get(0).desProduto()).isEqualTo("Produto Corrente");

        assertThat(response.get(1).codProduto()).isEqualTo("0002");
        assertThat(response.get(1).desProduto()).isEqualTo("Produto Investimento");

        verify(produtoRepository).findByStaStatusOrderByDesProdutoAsc("A");
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoExistiremProdutosAtivos() {
        when(produtoRepository.findByStaStatusOrderByDesProdutoAsc("A"))
                .thenReturn(List.of());

        List<ProdutoResponseDTO> response = produtoService.listarProdutosAtivos();

        assertThat(response).isEmpty();

        verify(produtoRepository).findByStaStatusOrderByDesProdutoAsc("A");
    }
}
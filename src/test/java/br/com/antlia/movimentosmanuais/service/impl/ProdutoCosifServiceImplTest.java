package br.com.antlia.movimentosmanuais.service.impl;

import br.com.antlia.movimentosmanuais.dto.ProdutoCosifResponseDTO;
import br.com.antlia.movimentosmanuais.entity.Produto;
import br.com.antlia.movimentosmanuais.entity.ProdutoCosif;
import br.com.antlia.movimentosmanuais.entity.ProdutoCosifId;
import br.com.antlia.movimentosmanuais.repository.ProdutoCosifRepository;
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
class ProdutoCosifServiceImplTest {

    @Mock
    private ProdutoCosifRepository produtoCosifRepository;

    @InjectMocks
    private ProdutoCosifServiceImpl produtoCosifService;

    @Test
    void deveListarCosifsAtivosPorProduto() {
        Produto produto = new Produto();
        produto.setCodProduto("0001");
        produto.setDesProduto("Produto Corrente");
        produto.setStaStatus("A");

        ProdutoCosif cosif1 = new ProdutoCosif();
        cosif1.setId(new ProdutoCosifId("0001", "11000000001"));
        cosif1.setProduto(produto);
        cosif1.setCodClassificacao("110001");
        cosif1.setStaStatus("A");

        ProdutoCosif cosif2 = new ProdutoCosif();
        cosif2.setId(new ProdutoCosifId("0001", "11000000002"));
        cosif2.setProduto(produto);
        cosif2.setCodClassificacao("110002");
        cosif2.setStaStatus("A");

        when(produtoCosifRepository.findByIdCodProdutoAndStaStatusOrderByIdCodCosifAsc("0001", "A"))
                .thenReturn(List.of(cosif1, cosif2));

        List<ProdutoCosifResponseDTO> response = produtoCosifService.listarCosifsPorProduto("0001");

        assertThat(response).hasSize(2);

        assertThat(response.get(0).codProduto()).isEqualTo("0001");
        assertThat(response.get(0).codCosif()).isEqualTo("11000000001");
        assertThat(response.get(0).codClassificacao()).isEqualTo("110001");
        assertThat(response.get(0).descricaoCombo()).isEqualTo("11000000001 - 110001");

        assertThat(response.get(1).codProduto()).isEqualTo("0001");
        assertThat(response.get(1).codCosif()).isEqualTo("11000000002");
        assertThat(response.get(1).codClassificacao()).isEqualTo("110002");
        assertThat(response.get(1).descricaoCombo()).isEqualTo("11000000002 - 110002");

        verify(produtoCosifRepository)
                .findByIdCodProdutoAndStaStatusOrderByIdCodCosifAsc("0001", "A");
    }

    @Test
    void deveRetornarListaVaziaQuandoProdutoNaoPossuirCosifsAtivos() {
        when(produtoCosifRepository.findByIdCodProdutoAndStaStatusOrderByIdCodCosifAsc("9999", "A"))
                .thenReturn(List.of());

        List<ProdutoCosifResponseDTO> response = produtoCosifService.listarCosifsPorProduto("9999");

        assertThat(response).isEmpty();

        verify(produtoCosifRepository)
                .findByIdCodProdutoAndStaStatusOrderByIdCodCosifAsc("9999", "A");
    }
}
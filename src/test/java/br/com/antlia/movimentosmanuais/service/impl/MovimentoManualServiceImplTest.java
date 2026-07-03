package br.com.antlia.movimentosmanuais.service.impl;

import br.com.antlia.movimentosmanuais.dto.MovimentoManualRequestDTO;
import br.com.antlia.movimentosmanuais.dto.MovimentoManualResponseDTO;
import br.com.antlia.movimentosmanuais.entity.MovimentoManual;
import br.com.antlia.movimentosmanuais.entity.MovimentoManualId;
import br.com.antlia.movimentosmanuais.entity.Produto;
import br.com.antlia.movimentosmanuais.entity.ProdutoCosif;
import br.com.antlia.movimentosmanuais.entity.ProdutoCosifId;
import br.com.antlia.movimentosmanuais.exception.NegocioException;
import br.com.antlia.movimentosmanuais.repository.MovimentoManualRepository;
import br.com.antlia.movimentosmanuais.repository.ProdutoCosifRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovimentoManualServiceImplTest {

    @Mock
    private MovimentoManualRepository movimentoManualRepository;

    @Mock
    private ProdutoCosifRepository produtoCosifRepository;

    @InjectMocks
    private MovimentoManualServiceImpl movimentoManualService;

    private Produto produto;
    private ProdutoCosif produtoCosif;

    @BeforeEach
    void setUp() {
        produto = new Produto();
        produto.setCodProduto("0001");
        produto.setDesProduto("Produto Corrente");
        produto.setStaStatus("A");

        ProdutoCosifId produtoCosifId = new ProdutoCosifId("0001", "11000000001");

        produtoCosif = new ProdutoCosif();
        produtoCosif.setId(produtoCosifId);
        produtoCosif.setProduto(produto);
        produtoCosif.setCodClassificacao("110001");
        produtoCosif.setStaStatus("A");
    }

    @Test
    void deveIncluirMovimentoManualComSucesso() {
        MovimentoManualRequestDTO request = new MovimentoManualRequestDTO(
                7,
                2026,
                "0001",
                "11000000001",
                new BigDecimal("2500.75"),
                "Movimento manual teste"
        );

        when(produtoCosifRepository.findById(new ProdutoCosifId("0001", "11000000001")))
                .thenReturn(Optional.of(produtoCosif));

        when(movimentoManualRepository.buscarUltimoLancamentoPorMesAno(7, 2026))
                .thenReturn(1L);

        when(movimentoManualRepository.save(any(MovimentoManual.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        MovimentoManualResponseDTO response = movimentoManualService.incluirMovimento(request);

        assertThat(response).isNotNull();
        assertThat(response.mes()).isEqualTo(7);
        assertThat(response.ano()).isEqualTo(2026);
        assertThat(response.numeroLancamento()).isEqualTo(2L);
        assertThat(response.codProduto()).isEqualTo("0001");
        assertThat(response.desProduto()).isEqualTo("Produto Corrente");
        assertThat(response.codCosif()).isEqualTo("11000000001");
        assertThat(response.codClassificacao()).isEqualTo("110001");
        assertThat(response.descricao()).isEqualTo("Movimento manual teste");
        assertThat(response.codUsuario()).isEqualTo("TESTE");
        assertThat(response.valor()).isEqualByComparingTo("2500.75");
        assertThat(response.dataMovimento()).isNotNull();

        ArgumentCaptor<MovimentoManual> captor = ArgumentCaptor.forClass(MovimentoManual.class);
        verify(movimentoManualRepository).save(captor.capture());

        MovimentoManual movimentoSalvo = captor.getValue();

        assertThat(movimentoSalvo.getId().getDatMes()).isEqualTo(7);
        assertThat(movimentoSalvo.getId().getDatAno()).isEqualTo(2026);
        assertThat(movimentoSalvo.getId().getNumLancamento()).isEqualTo(2L);
        assertThat(movimentoSalvo.getProdutoCosif()).isEqualTo(produtoCosif);
        assertThat(movimentoSalvo.getDesDescricao()).isEqualTo("Movimento manual teste");
        assertThat(movimentoSalvo.getValValor()).isEqualByComparingTo("2500.75");
        assertThat(movimentoSalvo.getCodUsuario()).isEqualTo("TESTE");
        assertThat(movimentoSalvo.getDatMovimento()).isNotNull();
    }

    @Test
    void deveGerarPrimeiroLancamentoQuandoNaoExistirMovimentoNoMesAno() {
        MovimentoManualRequestDTO request = new MovimentoManualRequestDTO(
                8,
                2026,
                "0001",
                "11000000001",
                new BigDecimal("100.00"),
                "Primeiro movimento"
        );

        when(produtoCosifRepository.findById(new ProdutoCosifId("0001", "11000000001")))
                .thenReturn(Optional.of(produtoCosif));

        when(movimentoManualRepository.buscarUltimoLancamentoPorMesAno(8, 2026))
                .thenReturn(0L);

        when(movimentoManualRepository.save(any(MovimentoManual.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        MovimentoManualResponseDTO response = movimentoManualService.incluirMovimento(request);

        assertThat(response.numeroLancamento()).isEqualTo(1L);
        assertThat(response.mes()).isEqualTo(8);
        assertThat(response.ano()).isEqualTo(2026);
    }

    @Test
    void deveLancarNegocioExceptionQuandoProdutoCosifNaoExistir() {
        MovimentoManualRequestDTO request = new MovimentoManualRequestDTO(
                7,
                2026,
                "9999",
                "99999999999",
                new BigDecimal("100.00"),
                "Movimento inválido"
        );

        when(produtoCosifRepository.findById(new ProdutoCosifId("9999", "99999999999")))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> movimentoManualService.incluirMovimento(request))
                .isInstanceOf(NegocioException.class)
                .hasMessage("Produto/Cosif não encontrado");
    }

    @Test
    void deveListarMovimentosManuais() {
        MovimentoManual movimentoManual = new MovimentoManual();
        movimentoManual.setId(new MovimentoManualId(7, 2026, 1L));
        movimentoManual.setProdutoCosif(produtoCosif);
        movimentoManual.setDesDescricao("Movimento inicial");
        movimentoManual.setValValor(new BigDecimal("1000.00"));
        movimentoManual.setCodUsuario("TESTE");
        movimentoManual.setDatMovimento(java.time.LocalDateTime.now());

        when(movimentoManualRepository.findAll())
                .thenReturn(List.of(movimentoManual));

        List<MovimentoManualResponseDTO> response = movimentoManualService.listarMovimentos();

        assertThat(response).hasSize(1);

        MovimentoManualResponseDTO movimento = response.getFirst();

        assertThat(movimento.mes()).isEqualTo(7);
        assertThat(movimento.ano()).isEqualTo(2026);
        assertThat(movimento.numeroLancamento()).isEqualTo(1L);
        assertThat(movimento.codProduto()).isEqualTo("0001");
        assertThat(movimento.desProduto()).isEqualTo("Produto Corrente");
        assertThat(movimento.codCosif()).isEqualTo("11000000001");
        assertThat(movimento.codClassificacao()).isEqualTo("110001");
        assertThat(movimento.descricao()).isEqualTo("Movimento inicial");
        assertThat(movimento.codUsuario()).isEqualTo("TESTE");
        assertThat(movimento.valor()).isEqualByComparingTo("1000.00");
    }
}
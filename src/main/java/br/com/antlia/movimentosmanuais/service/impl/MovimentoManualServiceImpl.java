package br.com.antlia.movimentosmanuais.service.impl;

import br.com.antlia.movimentosmanuais.dto.MovimentoManualRequestDTO;
import br.com.antlia.movimentosmanuais.dto.MovimentoManualResponseDTO;
import br.com.antlia.movimentosmanuais.entity.MovimentoManual;
import br.com.antlia.movimentosmanuais.entity.MovimentoManualId;
import br.com.antlia.movimentosmanuais.entity.ProdutoCosif;
import br.com.antlia.movimentosmanuais.entity.ProdutoCosifId;
import br.com.antlia.movimentosmanuais.exception.NegocioException;
import br.com.antlia.movimentosmanuais.repository.MovimentoManualRepository;
import br.com.antlia.movimentosmanuais.repository.ProdutoCosifRepository;
import br.com.antlia.movimentosmanuais.service.MovimentoManualService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovimentoManualServiceImpl implements MovimentoManualService {

    private static final String USUARIO_PADRAO = "TESTE";

    private final MovimentoManualRepository movimentoManualRepository;
    private final ProdutoCosifRepository produtoCosifRepository;

    @Override
    public List<MovimentoManualResponseDTO> listarMovimentos() {
        return movimentoManualRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional
    public MovimentoManualResponseDTO incluirMovimento(MovimentoManualRequestDTO request) {
        ProdutoCosif produtoCosif = buscarProdutoCosif(request.codProduto(), request.codCosif());

        Long proximoLancamento = gerarProximoLancamento(request.mes(), request.ano());

        MovimentoManual movimentoManual = new MovimentoManual();
        movimentoManual.setId(new MovimentoManualId(
                request.mes(),
                request.ano(),
                proximoLancamento
        ));
        movimentoManual.setProdutoCosif(produtoCosif);
        movimentoManual.setDesDescricao(request.descricao());
        movimentoManual.setValValor(request.valor());
        movimentoManual.setCodUsuario(USUARIO_PADRAO);
        movimentoManual.setDatMovimento(LocalDateTime.now());

        MovimentoManual movimentoSalvo = movimentoManualRepository.save(movimentoManual);

        return toResponseDTO(movimentoSalvo);
    }

    private ProdutoCosif buscarProdutoCosif(String codProduto, String codCosif) {
        ProdutoCosifId id = new ProdutoCosifId(codProduto, codCosif);

        return produtoCosifRepository.findById(id)
                .orElseThrow(() -> new NegocioException("Produto/Cosif não encontrado"));
    }

    private Long gerarProximoLancamento(Integer mes, Integer ano) {
        Long ultimoLancamento = movimentoManualRepository.buscarUltimoLancamentoPorMesAno(mes, ano);
        return ultimoLancamento + 1;
    }

    private MovimentoManualResponseDTO toResponseDTO(MovimentoManual movimento) {
        ProdutoCosif produtoCosif = movimento.getProdutoCosif();

        return new MovimentoManualResponseDTO(
                movimento.getId().getDatMes(),
                movimento.getId().getDatAno(),
                movimento.getId().getNumLancamento(),
                produtoCosif.getId().getCodProduto(),
                produtoCosif.getProduto().getDesProduto(),
                produtoCosif.getId().getCodCosif(),
                produtoCosif.getCodClassificacao(),
                movimento.getDesDescricao(),
                movimento.getDatMovimento(),
                movimento.getCodUsuario(),
                movimento.getValValor()
        );
    }
}
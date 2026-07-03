package br.com.antlia.movimentosmanuais.service.impl;

import br.com.antlia.movimentosmanuais.dto.ProdutoCosifResponseDTO;
import br.com.antlia.movimentosmanuais.repository.ProdutoCosifRepository;
import br.com.antlia.movimentosmanuais.service.ProdutoCosifService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoCosifServiceImpl implements ProdutoCosifService {

    private static final String STATUS_ATIVO = "A";

    private final ProdutoCosifRepository produtoCosifRepository;

    @Override
    public List<ProdutoCosifResponseDTO> listarCosifsPorProduto(String codProduto) {
        return produtoCosifRepository
                .findByIdCodProdutoAndStaStatusOrderByIdCodCosifAsc(codProduto, STATUS_ATIVO)
                .stream()
                .map(cosif -> new ProdutoCosifResponseDTO(
                        cosif.getId().getCodProduto(),
                        cosif.getId().getCodCosif(),
                        cosif.getCodClassificacao(),
                        montarDescricaoCombo(cosif.getId().getCodCosif(), cosif.getCodClassificacao())
                ))
                .toList();
    }

    private String montarDescricaoCombo(String codCosif, String codClassificacao) {
        return codCosif + " - " + codClassificacao;
    }
}
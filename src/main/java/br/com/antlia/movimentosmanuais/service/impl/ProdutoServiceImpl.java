package br.com.antlia.movimentosmanuais.service.impl;

import br.com.antlia.movimentosmanuais.dto.ProdutoResponseDTO;
import br.com.antlia.movimentosmanuais.repository.ProdutoRepository;
import br.com.antlia.movimentosmanuais.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoServiceImpl implements ProdutoService {

    private static final String STATUS_ATIVO = "A";

    private final ProdutoRepository produtoRepository;

    @Override
    public List<ProdutoResponseDTO> listarProdutosAtivos() {
        return produtoRepository.findByStaStatusOrderByDesProdutoAsc(STATUS_ATIVO)
                .stream()
                .map(produto -> new ProdutoResponseDTO(
                        produto.getCodProduto(),
                        produto.getDesProduto()
                ))
                .toList();
    }
}
package br.com.antlia.movimentosmanuais.service;

import br.com.antlia.movimentosmanuais.dto.ProdutoResponseDTO;

import java.util.List;

public interface ProdutoService {

    List<ProdutoResponseDTO> listarProdutosAtivos();
}
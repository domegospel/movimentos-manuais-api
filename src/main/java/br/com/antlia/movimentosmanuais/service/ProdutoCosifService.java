package br.com.antlia.movimentosmanuais.service;

import br.com.antlia.movimentosmanuais.dto.ProdutoCosifResponseDTO;

import java.util.List;

public interface ProdutoCosifService {

    List<ProdutoCosifResponseDTO> listarCosifsPorProduto(String codProduto);
}
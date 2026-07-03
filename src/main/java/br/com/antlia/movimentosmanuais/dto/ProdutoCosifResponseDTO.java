package br.com.antlia.movimentosmanuais.dto;

public record ProdutoCosifResponseDTO(
        String codProduto,
        String codCosif,
        String codClassificacao,
        String descricaoCombo
) {
}
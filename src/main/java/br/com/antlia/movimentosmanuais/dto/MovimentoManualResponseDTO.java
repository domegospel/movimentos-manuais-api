package br.com.antlia.movimentosmanuais.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MovimentoManualResponseDTO(
        Integer mes,
        Integer ano,
        Long numeroLancamento,
        String codProduto,
        String desProduto,
        String codCosif,
        String codClassificacao,
        String descricao,
        LocalDateTime dataMovimento,
        String codUsuario,
        BigDecimal valor
) {
}
package br.com.antlia.movimentosmanuais.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ErroResponseDTO(
        LocalDateTime timestamp,
        Integer status,
        String erro,
        List<String> mensagens
) {
}
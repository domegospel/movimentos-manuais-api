package br.com.antlia.movimentosmanuais.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record MovimentoManualRequestDTO(

        @NotNull(message = "O mês é obrigatório")
        @Min(value = 1, message = "O mês deve ser maior ou igual a 1")
        @Max(value = 12, message = "O mês deve ser menor ou igual a 12")
        Integer mes,

        @NotNull(message = "O ano é obrigatório")
        @Min(value = 1900, message = "O ano deve ser maior ou igual a 1900")
        @Max(value = 9999, message = "O ano deve ser menor ou igual a 9999")
        Integer ano,

        @NotBlank(message = "O código do produto é obrigatório")
        @Size(max = 4, message = "O código do produto deve ter no máximo 4 caracteres")
        String codProduto,

        @NotBlank(message = "O código Cosif é obrigatório")
        @Size(max = 11, message = "O código Cosif deve ter no máximo 11 caracteres")
        String codCosif,

        @NotNull(message = "O valor é obrigatório")
        @DecimalMin(value = "0.01", message = "O valor deve ser maior que zero")
        @Digits(integer = 16, fraction = 2, message = "O valor deve ter no máximo 16 dígitos inteiros e 2 casas decimais")
        BigDecimal valor,

        @NotBlank(message = "A descrição é obrigatória")
        @Size(max = 50, message = "A descrição deve ter no máximo 50 caracteres")
        String descricao
) {
}
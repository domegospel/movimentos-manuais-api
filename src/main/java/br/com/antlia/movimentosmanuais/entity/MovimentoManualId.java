package br.com.antlia.movimentosmanuais.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class MovimentoManualId implements Serializable {

    @Column(name = "DAT_MES", nullable = false)
    private Integer datMes;

    @Column(name = "DAT_ANO", nullable = false)
    private Integer datAno;

    @Column(name = "NUM_LANCAMENTO", nullable = false)
    private Long numLancamento;
}
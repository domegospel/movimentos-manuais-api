package br.com.antlia.movimentosmanuais.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "MOVIMENTO_MANUAL")
public class MovimentoManual {

    @EmbeddedId
    private MovimentoManualId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "COD_PRODUTO", referencedColumnName = "COD_PRODUTO", nullable = false),
            @JoinColumn(name = "COD_COSIF", referencedColumnName = "COD_COSIF", nullable = false)
    })
    private ProdutoCosif produtoCosif;

    @Column(name = "DES_DESCRICAO", length = 50, nullable = false)
    private String desDescricao;

    @Column(name = "DAT_MOVIMENTO", nullable = false)
    private LocalDateTime datMovimento;

    @Column(name = "COD_USUARIO", length = 15, nullable = false)
    private String codUsuario;

    @Column(name = "VAL_VALOR", precision = 18, scale = 2, nullable = false)
    private BigDecimal valValor;
}
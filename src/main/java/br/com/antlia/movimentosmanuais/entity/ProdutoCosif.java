package br.com.antlia.movimentosmanuais.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "PRODUTO_COSIF")
public class ProdutoCosif {

    @EmbeddedId
    private ProdutoCosifId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("codProduto")
    @JoinColumn(name = "COD_PRODUTO", nullable = false)
    private Produto produto;

    @Column(name = "COD_CLASSIFICACAO", length = 6)
    private String codClassificacao;

    @Column(name = "STA_STATUS", length = 1)
    private String staStatus;
}
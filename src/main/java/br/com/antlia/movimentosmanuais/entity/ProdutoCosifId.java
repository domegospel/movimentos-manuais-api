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
public class ProdutoCosifId implements Serializable {

    @Column(name = "COD_PRODUTO", length = 4, nullable = false)
    private String codProduto;

    @Column(name = "COD_COSIF", length = 11, nullable = false)
    private String codCosif;
}
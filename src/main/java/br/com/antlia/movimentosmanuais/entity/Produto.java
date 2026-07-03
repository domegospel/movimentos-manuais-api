package br.com.antlia.movimentosmanuais.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "PRODUTO")
public class Produto {

    @Id
    @Column(name = "COD_PRODUTO", length = 4, nullable = false)
    private String codProduto;

    @Column(name = "DES_PRODUTO", length = 30, nullable = false)
    private String desProduto;

    @Column(name = "STA_STATUS", length = 1)
    private String staStatus;
}
package br.com.antlia.movimentosmanuais.repository;

import br.com.antlia.movimentosmanuais.entity.ProdutoCosif;
import br.com.antlia.movimentosmanuais.entity.ProdutoCosifId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoCosifRepository extends JpaRepository<ProdutoCosif, ProdutoCosifId> {

    List<ProdutoCosif> findByIdCodProdutoAndStaStatusOrderByIdCodCosifAsc(
            String codProduto,
            String staStatus
    );
}
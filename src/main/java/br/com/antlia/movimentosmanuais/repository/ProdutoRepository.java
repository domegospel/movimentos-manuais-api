package br.com.antlia.movimentosmanuais.repository;

import br.com.antlia.movimentosmanuais.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, String> {

    List<Produto> findByStaStatusOrderByDesProdutoAsc(String staStatus);
}
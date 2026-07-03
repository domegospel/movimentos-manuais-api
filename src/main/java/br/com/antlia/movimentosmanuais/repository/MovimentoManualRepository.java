package br.com.antlia.movimentosmanuais.repository;

import br.com.antlia.movimentosmanuais.entity.MovimentoManual;
import br.com.antlia.movimentosmanuais.entity.MovimentoManualId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MovimentoManualRepository extends JpaRepository<MovimentoManual, MovimentoManualId> {

    @Query("""
            SELECT COALESCE(MAX(m.id.numLancamento), 0)
            FROM MovimentoManual m
            WHERE m.id.datMes = :datMes
              AND m.id.datAno = :datAno
            """)
    Long buscarUltimoLancamentoPorMesAno(Integer datMes, Integer datAno);
}
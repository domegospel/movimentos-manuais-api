package br.com.antlia.movimentosmanuais.service;

import br.com.antlia.movimentosmanuais.dto.MovimentoManualRequestDTO;
import br.com.antlia.movimentosmanuais.dto.MovimentoManualResponseDTO;

import java.util.List;

public interface MovimentoManualService {

    List<MovimentoManualResponseDTO> listarMovimentos();

    MovimentoManualResponseDTO incluirMovimento(MovimentoManualRequestDTO request);
}
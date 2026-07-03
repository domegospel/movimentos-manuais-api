package br.com.antlia.movimentosmanuais.controller;

import br.com.antlia.movimentosmanuais.dto.MovimentoManualRequestDTO;
import br.com.antlia.movimentosmanuais.dto.MovimentoManualResponseDTO;
import br.com.antlia.movimentosmanuais.service.MovimentoManualService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movimentos-manuais")
@RequiredArgsConstructor
public class MovimentoManualController {

    private final MovimentoManualService movimentoManualService;

    @GetMapping
    public List<MovimentoManualResponseDTO> listarMovimentos() {
        return movimentoManualService.listarMovimentos();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovimentoManualResponseDTO incluirMovimento(
            @RequestBody @Valid MovimentoManualRequestDTO request
    ) {
        return movimentoManualService.incluirMovimento(request);
    }
}
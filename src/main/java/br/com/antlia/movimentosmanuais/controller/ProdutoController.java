package br.com.antlia.movimentosmanuais.controller;

import br.com.antlia.movimentosmanuais.dto.ProdutoResponseDTO;
import br.com.antlia.movimentosmanuais.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;

    @GetMapping
    public List<ProdutoResponseDTO> listarProdutosAtivos() {
        return produtoService.listarProdutosAtivos();
    }
}
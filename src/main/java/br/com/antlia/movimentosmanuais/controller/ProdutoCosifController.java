package br.com.antlia.movimentosmanuais.controller;

import br.com.antlia.movimentosmanuais.dto.ProdutoCosifResponseDTO;
import br.com.antlia.movimentosmanuais.service.ProdutoCosifService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProdutoCosifController {

    private final ProdutoCosifService produtoCosifService;

    @GetMapping("/{codProduto}/cosifs")
    public List<ProdutoCosifResponseDTO> listarCosifsPorProduto(
            @PathVariable String codProduto
    ) {
        return produtoCosifService.listarCosifsPorProduto(codProduto);
    }
}
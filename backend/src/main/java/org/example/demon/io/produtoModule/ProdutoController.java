package org.example.demon.io.produtoModule;

import org.example.demon.io.commonModule.LoginDto;
import org.example.demon.io.models.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<String> criar(@RequestBody ProdutoDto dto) {
        produtoService.criarProduto(dto);
        return ResponseEntity.status(201).body("Produto cadastrado!");
    }

    @GetMapping
    public List<Produto> listarTodos() {
        return produtoService.buscarTodos();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable int id, @RequestBody LoginDto loginDto) {
        try {
            produtoService.deletarProduto(loginDto, id);
            return ResponseEntity.ok("Produto removido!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }
}
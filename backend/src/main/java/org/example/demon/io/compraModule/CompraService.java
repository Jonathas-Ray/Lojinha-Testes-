package org.example.demon.io.compraModule;

import java.util.List;
import org.example.demon.io.models.Compra;
import org.example.demon.io.models.Produto;
import org.example.demon.io.models.Usuario;
import org.example.demon.io.produtoModule.ProdutoRepository;
import org.example.demon.io.usuarioModule.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CompraService {
    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Transactional
    public void finalizarCompra(List<Integer> produtosIds) {

        String emailLogado = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario comprador = usuarioService.buscarPorEmail(emailLogado);

        Compra compra = new Compra();
        compra.setComprador(comprador);

        List<Produto> produtosParaComprar = produtoRepository.findAllById(produtosIds);

        if (produtosParaComprar.isEmpty()) {
            throw new RuntimeException("O carrinho está vazio ou os produtos não existem!");
        }

        for (Produto produto : produtosParaComprar) {

            if (produto.getCompra() != null) {
                throw new RuntimeException("O produto " + produto.getNome() + " já foi vendido!");
            }

            produto.setCompra(compra);
        }

        compra.setProdutos(produtosParaComprar);

        compraRepository.save(compra);
    }
}
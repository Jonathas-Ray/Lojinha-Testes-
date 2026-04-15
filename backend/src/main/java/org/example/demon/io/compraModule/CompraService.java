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
    private ProdutoRepository produtoRepository; // Precisamos para buscar os produtos

    @Autowired
    private UsuarioService usuarioService;

    @Transactional
    public void finalizarCompra(List<Integer> produtosIds) {
        // 1. Identificar o comprador pelo Token
        String emailLogado = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario comprador = usuarioService.buscarPorEmail(emailLogado);

        // 2. Criar a instância da Compra e associar o comprador
        Compra compra = new Compra();
        compra.setComprador(comprador);

        // 3. Buscar os produtos no banco pelos IDs enviados
        List<Produto> produtosParaComprar = produtoRepository.findAllById(produtosIds);

        if (produtosParaComprar.isEmpty()) {
            throw new RuntimeException("O carrinho está vazio ou os produtos não existem!");
        }

        // 4. Vincular cada produto à compra (IMPORTANTE para o JPA)
        for (Produto produto : produtosParaComprar) {
            // Validação opcional: O produto já foi comprado?
            if (produto.getCompra() != null) {
                throw new RuntimeException("O produto " + produto.getNome() + " já foi vendido!");
            }

            produto.setCompra(compra); // Seta o @ManyToOne no lado do Produto
        }

        // 5. Adicionar a lista de produtos à compra
        compra.setProdutos(produtosParaComprar);

        // 6. Salvar (O CascadeType.ALL vai cuidar de atualizar os produtos)
        compraRepository.save(compra);
    }
}
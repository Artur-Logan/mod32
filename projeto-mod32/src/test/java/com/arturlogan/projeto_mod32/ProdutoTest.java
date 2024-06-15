package com.arturlogan.projeto_mod32;

import com.arturlogan.projeto_mod32.entities.Produto;
import com.arturlogan.projeto_mod32.entities.Produto;
import com.arturlogan.projeto_mod32.services.interfaces.IProdutoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProdutoTest {

    @Autowired
    private IProdutoService produtoService;

    private Produto criarProduto(String codigo) {
        Produto produto = new Produto();
        produto.setCodigo(codigo);
        produto.setDescricao("Produto 1");
        produto.setNome("Produto 1");
        produto.setValor(BigDecimal.TEN);
        produtoService.cadastrar(produto);
        return produto;
    }

    @Test
    public void pesquisar(){
        Produto Produto = criarProduto("A1");
        assertNotNull(Produto);
        Produto ProdutoDB = this.produtoService.consultar(Produto.getId());
        assertNotNull(ProdutoDB);
    }

    @Test
    public void alterarProduto() {
        Produto produto = criarProduto("A4");
        produto.setNome("Rodrigo Pires");
        produtoService.atualizarDados(produto, produto.getId());
        Produto produtoBD = this.produtoService.consultar(produto.getId());
        assertNotNull(produtoBD);
        assertEquals("Rodrigo Pires", produtoBD.getNome());
    }

    @Test
    public void excluir() {
        Produto Produto = criarProduto("A3");
        assertNotNull(Produto);
        this.produtoService.excluir(Produto.getId());
        Produto ProdutoBD = this.produtoService.consultar(Produto.getId());
        assertNull(ProdutoBD);
    }

    @Test
    public void cadastrar() {
        Produto produto = criarProduto("P21");

        assertNotNull(produto);
    }

    @Test
    public void buscarTodos(){
        criarProduto("A5");
        criarProduto("A6");
        Collection<Produto> list = produtoService.listarTodos();
        assertTrue(list != null);
        assertTrue(list.size() == 2);

        for (Produto prod : list) {
            this.produtoService.excluir(prod.getId());
        }

        list = produtoService.listarTodos();
        assertTrue(list != null);
        assertTrue(list.size() == 0);

    }
}


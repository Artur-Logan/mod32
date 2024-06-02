package com.arturlogan.projeto_mod32.services;

import com.arturlogan.projeto_mod32.entities.Produto;
import com.arturlogan.projeto_mod32.repositories.ProdutoRepository;
import com.arturlogan.projeto_mod32.services.interfaces.IProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService implements IProdutoService {

    private final ProdutoRepository produtoRepository;

    @Autowired
    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Produto cadastrar(Produto produto) {
        return produtoRepository.save(produto);
    }

    public void excluir(Long id) {
        Produto produto = produtoRepository.getById(id);
        produtoRepository.delete(produto);
    }

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }
}

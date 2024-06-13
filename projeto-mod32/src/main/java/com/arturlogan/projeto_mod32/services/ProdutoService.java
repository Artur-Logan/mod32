package com.arturlogan.projeto_mod32.services;

import com.arturlogan.projeto_mod32.entities.Produto;
import com.arturlogan.projeto_mod32.repositories.ProdutoRepository;
import com.arturlogan.projeto_mod32.services.interfaces.IProdutoService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService implements IProdutoService {

    private final ProdutoRepository produtoRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Produto cadastrar(Produto produto) {
        // Verifica se já existe um produto com o mesmo código
        Optional<Produto> produtoExistente = produtoRepository.findByCodigo(produto.getCodigo());
        if (produtoExistente.isPresent()) {
            throw new DataIntegrityViolationException("Código do produto já existe: " + produto.getCodigo());
        }
        return produtoRepository.save(produto);
    }

    @Transactional
    @Override
    public Produto cadastrarNovo(Produto produto) {
        // Verifica se já existe um produto com o mesmo código
        Optional<Produto> produtoExistente = produtoRepository.findByCodigo(produto.getCodigo());
        if (produtoExistente.isPresent()) {
            throw new DataIntegrityViolationException("Código do produto já existe: " + produto.getCodigo());
        }
        return entityManager.merge(produto);
    }

    public void excluir(Long id) {
        Produto produto = produtoRepository.getById(id);
        produtoRepository.delete(produto);
    }

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }
}
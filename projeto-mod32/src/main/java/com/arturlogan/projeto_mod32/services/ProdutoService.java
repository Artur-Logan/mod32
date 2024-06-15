package com.arturlogan.projeto_mod32.services;

import com.arturlogan.projeto_mod32.entities.Cliente;
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

    @Override
    public Produto atualizarDados(Produto entity, Long id) {
        Produto produto = produtoRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        // Atualiza apenas os campos que foram fornecidos na entidade `entity`
        if (entity.getNome() != null) produto.setNome(entity.getNome());
        if (entity.getCodigo() != null) produto.setCodigo(entity.getCodigo());
        if (entity.getValor() != null) produto.setNome(entity.getNome());
        if (entity.getDescricao() != null) produto.setDescricao(entity.getDescricao());

        return produtoRepository.save(produto);
    }

    @Override
    public Produto consultar(Long id) {
        Optional<Produto> produto = produtoRepository.findById(id);

        return produto.orElse(null);
    }

    public void excluir(Long id) {
        Produto produto = produtoRepository.getById(id);
        produtoRepository.delete(produto);
    }

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }
}
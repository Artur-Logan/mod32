package com.arturlogan.projeto_mod32.services.interfaces;

import com.arturlogan.projeto_mod32.entities.Cliente;
import com.arturlogan.projeto_mod32.entities.Produto;

import java.util.List;

public interface IProdutoService {

    public Produto cadastrar(Produto produto);

    public Produto cadastrarNovo(Produto produto);

    public Produto atualizarDados(Produto entity, Long id);

    public Produto consultar(Long id);

    public void excluir(Long id);

    public List<Produto> listarTodos();


}

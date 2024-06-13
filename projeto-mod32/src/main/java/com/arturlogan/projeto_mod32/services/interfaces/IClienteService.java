package com.arturlogan.projeto_mod32.services.interfaces;

import com.arturlogan.projeto_mod32.entities.Cliente;
import com.arturlogan.projeto_mod32.entities.Produto;

import java.util.List;

public interface IClienteService {

    public Cliente cadastrar(Cliente cliente);


    public Cliente atualizarDados(Cliente entity, Long id);

    public Cliente consultar(Long id);

    public void excluir(Long id);

    public List<Cliente> listarTodos();



}

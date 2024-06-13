package com.arturlogan.projeto_mod32.services.interfaces;

import com.arturlogan.projeto_mod32.entities.Cliente;
import com.arturlogan.projeto_mod32.entities.Venda;

import java.util.List;

public interface IVendaService {

    public Venda cadastrar(Venda venda);

    //public Venda cadastrarNovo(Venda venda);

    public void excluir(Long id);

    public List<Venda> listarTodos();

    public Venda consultar(Long id);


}

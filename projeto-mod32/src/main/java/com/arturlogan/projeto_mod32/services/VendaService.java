package com.arturlogan.projeto_mod32.services;

import com.arturlogan.projeto_mod32.entities.Cliente;
import com.arturlogan.projeto_mod32.entities.Venda;
import com.arturlogan.projeto_mod32.repositories.ClienteRepository;
import com.arturlogan.projeto_mod32.repositories.VendaRepository;
import com.arturlogan.projeto_mod32.services.interfaces.IClienteService;
import com.arturlogan.projeto_mod32.services.interfaces.IVendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendaService implements IVendaService {

    private final VendaRepository vendaRepository;

    @Autowired
    public VendaService(VendaRepository vendaRepository) {
        this.vendaRepository = vendaRepository;
    }

    public Venda cadastrar(Venda venda) {
        return vendaRepository.save(venda);
    }

    public void excluir(Long id) {
        Venda venda = vendaRepository.getById(id);
        vendaRepository.delete(venda);
    }

    public Venda consultar(Long id){
        Venda venda = vendaRepository.findById(id).get();
         return venda;
    }

    public List<Venda> listarTodos() {
        return vendaRepository.findAll();
    }
}

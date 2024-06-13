package com.arturlogan.projeto_mod32.services;

import com.arturlogan.projeto_mod32.entities.Cliente;
import com.arturlogan.projeto_mod32.repositories.ClienteRepository;
import com.arturlogan.projeto_mod32.services.interfaces.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService implements IClienteService {

    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente cadastrar(Cliente Cliente) {
        return clienteRepository.save(Cliente);
    }

    @Override
    public Cliente atualizarDados(Cliente entity, Long id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        // Atualiza apenas os campos que foram fornecidos na entidade `entity`
        if (entity.getNome() != null) cliente.setNome(entity.getNome());
        if (entity.getNumero() != null) cliente.setNumero(entity.getNumero());
        if (entity.getTel() != null) cliente.setTel(entity.getTel());
        if (entity.getEstado() != null) cliente.setEstado(entity.getEstado());
        if (entity.getCpf() != null) cliente.setCpf(entity.getCpf());
        if (entity.getEnd() != null) cliente.setEnd(entity.getEnd());
        if (entity.getCidade() != null) cliente.setCidade(entity.getCidade());

        return clienteRepository.save(cliente);
    }


    @Override
    public Cliente consultar(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);

        return cliente.orElse(null);
    }

    public void excluir(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        clienteRepository.deleteById(cliente.get().getId());
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }
}

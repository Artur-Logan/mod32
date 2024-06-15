package com.arturlogan.projeto_mod32.services;

import com.arturlogan.projeto_mod32.entities.Venda;
import com.arturlogan.projeto_mod32.repositories.VendaRepository;
import com.arturlogan.projeto_mod32.services.interfaces.IVendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VendaService implements IVendaService {

    private final VendaRepository vendaRepository;

    @Autowired
    public VendaService(VendaRepository vendaRepository) {
        this.vendaRepository = vendaRepository;
    }

    public Venda cadastrar(Venda venda) {
        // Verificar se já existe uma venda com o mesmo código
        Optional<Venda> vendaExistente = vendaRepository.findByCodigo(venda.getCodigo());
        if (vendaExistente.isPresent()) {
            throw new DataIntegrityViolationException("Já existe uma venda com o código: " + venda.getCodigo());
        }
        return vendaRepository.save(venda);
    }

    public void excluir(Long id) {
        Venda venda = vendaRepository.findById(id).orElseThrow(() -> new RuntimeException("Venda não encontrada"));
        vendaRepository.delete(venda);
    }

    public Venda consultar(Long id) {
        return vendaRepository.findById(id).orElse(null);
    }

    @Override
    public Venda atualizarDados(Venda entity, Long id) {
        Venda venda = vendaRepository.findById(id).orElseThrow(() -> new RuntimeException("Venda não encontrada"));

        // Atualiza apenas os campos que foram fornecidos na entidade `entity`
        if (entity.getCodigo() != null) venda.setCodigo(entity.getCodigo());
        if (entity.getStatus() != null) venda.setStatus(entity.getStatus());
        if (entity.getDataVenda() != null) venda.setDataVenda(entity.getDataVenda());
        if (entity.getCliente() != null) venda.setCliente(entity.getCliente());
        if (entity.getProdutos() != null) venda.setProdutos(entity.getProdutos());
        if (entity.getValorTotal() != null) venda.setValorTotal(entity.getValorTotal());

        return vendaRepository.save(venda);
    }

    public List<Venda> listarTodos() {
        return vendaRepository.findAll();
    }

    public Venda consultarComCollection(Long id) {
        return vendaRepository.findByIdWithCollections(id).orElse(null);
    }
}

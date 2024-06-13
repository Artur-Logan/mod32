package com.arturlogan.projeto_mod32;

import com.arturlogan.projeto_mod32.entities.Cliente;
import com.arturlogan.projeto_mod32.entities.Produto;
import com.arturlogan.projeto_mod32.entities.Venda;
import com.arturlogan.projeto_mod32.services.interfaces.IClienteService;
import com.arturlogan.projeto_mod32.services.interfaces.IProdutoService;
import com.arturlogan.projeto_mod32.services.interfaces.IVendaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class VendaTest {

    @Autowired
    private IVendaService vendaService;

    @Autowired
    private IClienteService clienteService;

    @Autowired
    private IProdutoService produtoService;

    @Test
    public void pesquisar() {
        Venda venda = criarVenda("A4");
        Venda retorno = vendaService.cadastrar(venda);
        assertNotNull(retorno);
        Venda vendaConsultada = vendaService.consultar(venda.getId());
        assertNotNull(vendaConsultada);
        assertEquals(venda.getCodigo(), vendaConsultada.getCodigo());
    }

    private Venda criarVenda(String codigo) {
        Venda venda = new Venda();
        venda.setCodigo(codigo);
        venda.setDataVenda(Instant.now());
        venda.setCliente(cadastrarCliente());
        venda.setStatus(Venda.Status.INICIADA);
        venda.adicionarProduto(cadastrarProduto("A5", BigDecimal.TEN), 2);
        return venda;
    }

    private Cliente cadastrarCliente() {
        Cliente cliente = new Cliente();
        cliente.setCpf(12345l);
        cliente.setNome("Rodrigo");
        cliente.setCidade("São Paulo");
        cliente.setEnd("End");
        cliente.setEstado("SP");
        cliente.setNumero(10);
        cliente.setTel(1199999999L);
        clienteService.cadastrar(cliente);
        return cliente;
    }

    private Produto cadastrarProduto(String codigo, BigDecimal valor) {
        Produto produto = new Produto();
        produto.setCodigo(codigo);
        produto.setDescricao("Produto 1");
        produto.setNome("Produto 1");
        produto.setValor(valor);
        produtoService.cadastrarNovo(produto);
        return produto;
    }
}

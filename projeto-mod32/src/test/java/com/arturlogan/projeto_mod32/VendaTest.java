package com.arturlogan.projeto_mod32;

import com.arturlogan.projeto_mod32.entities.Cliente;
import com.arturlogan.projeto_mod32.entities.Produto;
import com.arturlogan.projeto_mod32.entities.Venda;
import com.arturlogan.projeto_mod32.exceptions.EntityException;
import com.arturlogan.projeto_mod32.services.interfaces.IClienteService;
import com.arturlogan.projeto_mod32.services.interfaces.IProdutoService;
import com.arturlogan.projeto_mod32.services.interfaces.IVendaService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VendaTest {

    @Autowired
    private IVendaService vendaService;

    @Autowired
    private IClienteService clienteService;

    @Autowired
    private IProdutoService produtoService;

    @Autowired
    private Produto produto = cadastrarProduto("A1", BigDecimal.TEN);

    @Test
    public void pesquisar() {
        Venda venda = criarVenda("A4");
        Venda retorno = vendaService.cadastrar(venda);
        assertNotNull(retorno);
        Venda vendaConsultada = vendaService.consultar(venda.getId());
        assertNotNull(vendaConsultada);
        assertEquals(venda.getCodigo(), vendaConsultada.getCodigo());
    }

    @Test
    public void salvar() {
        Venda venda = criarVenda("A2");
        Venda retorno = vendaService.cadastrar(venda);
        assertNotNull(retorno);

        assertTrue(venda.getValorTotal().equals(BigDecimal.valueOf(20)));
        assertTrue(venda.getStatus().equals(Venda.Status.INICIADA));

        Venda vendaConsultada = vendaService.consultar(venda.getId());
        assertTrue(vendaConsultada.getId() != null);
        assertEquals(venda.getCodigo(), vendaConsultada.getCodigo());
    }

    @Test
    public void finalizarVenda() {
        Venda venda = criarVenda("A&3");
        Venda vendaSalva = vendaService.cadastrar(venda);
        assertNotNull(vendaSalva);

        // Atualizar a venda
        Venda vendaAtualizada = new Venda();
        vendaAtualizada.setId(vendaSalva.getId());
        vendaAtualizada.setStatus(Venda.Status.CONCLUIDA);
        vendaService.atualizarDados(vendaAtualizada, vendaSalva.getId());

        Venda vendaConsultada = vendaService.consultar(vendaSalva.getId());
        assertNotNull(vendaConsultada);
        assertEquals(vendaAtualizada.getStatus(), vendaConsultada.getStatus());

//        Excluir a venda
//        vendaService.excluir(vendaSalva.getId());
//        vendaConsultada = vendaService.consultar(vendaSalva.getId());
//        assertNull(vendaConsultada);
    }

    @Test
    public void cancelarVenda() {
        Venda venda = criarVenda("A&3");
        Venda vendaSalva = vendaService.cadastrar(venda);
        assertNotNull(vendaSalva);

        // Atualizar a venda
        Venda vendaAtualizada = new Venda();
        vendaAtualizada.setId(vendaSalva.getId());
        vendaAtualizada.setStatus(Venda.Status.CANCELADA);
        vendaService.atualizarDados(vendaAtualizada, vendaSalva.getId());

        Venda vendaConsultada = vendaService.consultar(vendaSalva.getId());
        assertNotNull(vendaConsultada);
        assertEquals(vendaAtualizada.getStatus(), vendaConsultada.getStatus());

        // Excluir a venda
        vendaService.excluir(vendaSalva.getId());
        vendaConsultada = vendaService.consultar(vendaSalva.getId());
        assertNull(vendaConsultada);
    }


    @Test
    public void adicionarMaisProdutosDoMesmo(){
        String codigoVenda = "A4";
        Venda venda = criarVenda(codigoVenda);
        Venda retorno = vendaService.cadastrar(venda);
        assertNotNull(retorno);
        assertNotNull(venda);
        assertEquals(codigoVenda, venda.getCodigo());

        Venda vendaConsultada = vendaService.consultarComCollection(venda.getId());
        vendaConsultada.adicionarProduto(cadastrarProduto("Produto !", BigDecimal.TEN), 1);

        assertTrue(vendaConsultada.getQuantidadeTotalProdutos() == 3);
        BigDecimal valorTotal = BigDecimal.valueOf(30).setScale(2, RoundingMode.HALF_DOWN);
        assertTrue(vendaConsultada.getValorTotal().equals(valorTotal));
        assertTrue(vendaConsultada.getStatus().equals(Venda.Status.INICIADA));
    }

    @Test
    public void adicionarMaisProdutosDiferentes() {
        String codigoVenda = "A5";
        Venda venda = criarVenda(codigoVenda);
        Venda retorno = vendaService.cadastrar(venda);
        assertNotNull(retorno);
        assertNotNull(venda);
        assertEquals(codigoVenda, venda.getCodigo());

        Produto prod = cadastrarProduto("Produto1", BigDecimal.valueOf(50));
        assertNotNull(prod);
        assertEquals("Produto1", prod.getCodigo());

        // Usando este método para evitar a exception org.hibernate.LazyInitializationException
        // Ele busca todos os dados da lista pois a mesma por default é lazy
        Venda vendaConsultada = vendaService.consultarComCollection(venda.getId());
        vendaConsultada.adicionarProduto(prod, 1);

        assertEquals(3, vendaConsultada.getQuantidadeTotalProdutos());
        BigDecimal valorTotal = BigDecimal.valueOf(70).setScale(2, RoundingMode.HALF_DOWN);
        assertEquals(valorTotal, vendaConsultada.getValorTotal());
        assertEquals(Venda.Status.INICIADA, vendaConsultada.getStatus());
    }

//    @Test()
//    public void salvarVendaMesmoCodigoExistente(){
//        Venda venda = criarVenda("A6");
//        Venda retorno = vendaService.cadastrar(venda);
//        assertNotNull(retorno);
//
//        Venda venda1 = criarVenda("A6");
//        Venda retorno1 = vendaService.cadastrar(venda1);
//        assertNull(retorno1);

//        assertTrue(venda.getStatus().equals(Venda.Status.INICIADA));
//    }
    @Test
    public void removerProduto()  {
        String codigoVenda = "A7";
        Venda venda = criarVenda(codigoVenda);
        Venda retorno = vendaService.cadastrar(venda);
        assertNotNull(retorno);
        assertNotNull(venda);
        assertEquals(codigoVenda, venda.getCodigo());

        Produto prod = cadastrarProduto(codigoVenda, BigDecimal.valueOf(50));
        assertNotNull(prod);
        assertEquals(codigoVenda, prod.getCodigo());

        Venda vendaConsultada = vendaService.consultarComCollection(venda.getId());
        vendaConsultada.adicionarProduto(prod, 1);
        assertTrue(vendaConsultada.getQuantidadeTotalProdutos() == 3);
        BigDecimal valorTotal = BigDecimal.valueOf(70).setScale(2, RoundingMode.HALF_DOWN);
        assertTrue(vendaConsultada.getValorTotal().equals(valorTotal));


        vendaConsultada.removerProduto(prod, 1);
        assertTrue(vendaConsultada.getQuantidadeTotalProdutos() == 2);
        valorTotal = BigDecimal.valueOf(20).setScale(2, RoundingMode.HALF_DOWN);
        assertTrue(vendaConsultada.getValorTotal().equals(valorTotal));
        assertTrue(vendaConsultada.getStatus().equals(Venda.Status.INICIADA));
    }

    @Test
    public void removerApenasUmProduto() {
        String codigoVenda = "A8";
        Venda venda = criarVenda(codigoVenda);
        Venda retorno = vendaService.cadastrar(venda);
        assertNotNull(retorno);
        assertNotNull(venda);
        assertEquals(codigoVenda, venda.getCodigo());

        Produto prod = cadastrarProduto(codigoVenda, BigDecimal.valueOf(50));
        assertNotNull(prod);
        assertEquals(codigoVenda, prod.getCodigo());

        Venda vendaConsultada = vendaService.consultarComCollection(venda.getId());
        vendaConsultada.adicionarProduto(prod, 1);
        assertTrue(vendaConsultada.getQuantidadeTotalProdutos() == 3);
        BigDecimal valorTotal = BigDecimal.valueOf(70).setScale(2, RoundingMode.HALF_DOWN);
        assertTrue(vendaConsultada.getValorTotal().equals(valorTotal));


        vendaConsultada.removerProduto(prod, 1);
        assertTrue(vendaConsultada.getQuantidadeTotalProdutos() == 2);
        valorTotal = BigDecimal.valueOf(20).setScale(2, RoundingMode.HALF_DOWN);
        assertTrue(vendaConsultada.getValorTotal().equals(valorTotal));
        assertTrue(vendaConsultada.getStatus().equals(Venda.Status.INICIADA));
    }

    @Test
    public void removerTodosProdutos(){
        String codigoVenda = "A9";
        Venda venda = criarVenda(codigoVenda);
        Venda retorno = vendaService.cadastrar(venda);
        assertNotNull(retorno);
        assertNotNull(venda);
        assertEquals(codigoVenda, venda.getCodigo());

        Produto prod = cadastrarProduto(codigoVenda, BigDecimal.valueOf(50));
        assertNotNull(prod);
        assertEquals(codigoVenda, prod.getCodigo());

        Venda vendaConsultada = vendaService.consultarComCollection(venda.getId());
        vendaConsultada.adicionarProduto(prod, 1);
        assertTrue(vendaConsultada.getQuantidadeTotalProdutos() == 3);
        BigDecimal valorTotal = BigDecimal.valueOf(70).setScale(2, RoundingMode.HALF_DOWN);
        assertTrue(vendaConsultada.getValorTotal().equals(valorTotal));


        vendaConsultada.removerTodosProdutos();
        assertTrue(vendaConsultada.getQuantidadeTotalProdutos() == 0);
        assertTrue(vendaConsultada.getValorTotal().equals(BigDecimal.valueOf(0)));
        assertTrue(vendaConsultada.getStatus().equals(Venda.Status.INICIADA));
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

    @Test
    public void salvarVendaMesmoCodigoExistente()  throws EntityException {
        Venda venda = criarVenda("A6");
        Venda retorno = vendaService.cadastrar(venda);
        assertNotNull(retorno);

        Cliente cliente = new Cliente();
        cliente.setCpf(12345L);
        cliente.setNome("Rodrigo");
        cliente.setCidade("São Paulo");
        cliente.setEnd("End");
        cliente.setEstado("SP");
        cliente.setNumero(10);
        cliente.setTel(1199999999L);

        Venda venda1 = new Venda();
        venda1.setCodigo("A6");
        venda1.setDataVenda(Instant.now());
        venda1.setCliente(cliente);
        venda1.setStatus(Venda.Status.INICIADA);
        venda1.adicionarProduto(cadastrarProduto("A67", BigDecimal.TEN), 2);

        assertThrows(DataIntegrityViolationException.class, () -> vendaService.cadastrar(venda1));
    }

//    @Test()
//    public void tentarAdicionarProdutosVendaFinalizada() {
//        String codigoVenda = "A11";
//        Venda venda = criarVenda(codigoVenda);
//        Venda retorno = vendaService.cadastrar(venda);
//        assertNotNull(retorno);
//        assertNotNull(venda);
//        assertEquals(codigoVenda, venda.getCodigo());
//
//        venda.setStatus(Venda.Status.CONCLUIDA);
//        vendaService.atualizarDados(venda, retorno.getId());
//
//        Venda vendaConsultada = vendaService.consultarComCollection(venda.getId());
//        assertEquals(venda.getCodigo(), vendaConsultada.getCodigo());
//        assertEquals(Venda.Status.CONCLUIDA, vendaConsultada.getStatus());
//
//        assertThrows(UnsupportedOperationException.class, () -> {
//            vendaConsultada.adicionarProduto(this.produto, 1);
//        });
//    }

    private Venda criarVenda(String codigo) {
        Venda venda = new Venda();
        venda.setCodigo(codigo);
        venda.setDataVenda(Instant.now());
        venda.setCliente(cadastrarCliente());
        venda.setStatus(Venda.Status.INICIADA);
        venda.adicionarProduto(cadastrarProduto("A5", BigDecimal.TEN), 2);
        return venda;
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

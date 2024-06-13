package com.arturlogan.projeto_mod32;

import com.arturlogan.projeto_mod32.entities.Cliente;
import com.arturlogan.projeto_mod32.services.interfaces.IClienteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ClienteTest {

    @Autowired
    private IClienteService clienteService;

    private Random rd = new Random();

    @Test
    public void cadastrar() {
        Cliente cliente = criarCliente();

        clienteService.cadastrar(cliente);
        assertNotNull(cliente);

        clienteService.excluir(cliente.getId());

        Cliente cliente1 = clienteService.consultar(cliente.getId());
        assertNull(cliente1);
    }

    @Test
    public void deletar(){
        Cliente cliente = criarCliente();
        Cliente cliente1 = clienteService.cadastrar(cliente);
        assertNotNull(cliente1);

        Cliente clienteConsultado = clienteService.consultar(cliente.getId());
        assertNotNull(clienteConsultado);

        clienteService.excluir(cliente.getId());
        clienteConsultado = clienteService.consultar(cliente.getId());
        assertNull(clienteConsultado);
    }

    @Test
    public void alterar() {
        Cliente cliente = criarCliente();
        Cliente clienteSalvo = clienteService.cadastrar(cliente);
        assertNotNull(clienteSalvo);

        Cliente clienteConsultado = criarCliente(); // Reutiliza o método para criar um cliente completo
        clienteConsultado.setNome("Nome Att");
        clienteService.atualizarDados(clienteConsultado, clienteSalvo.getId());

        Cliente clienteAlterado = clienteService.consultar(clienteSalvo.getId());
        assertNotNull(clienteAlterado);
        assertEquals("Nome Att", clienteAlterado.getNome());

        clienteService.excluir(clienteSalvo.getId());
        clienteConsultado = clienteService.consultar(clienteSalvo.getId());
        assertNull(clienteConsultado);
    }

    @Test
    public void buscarTodos() {
        Cliente cliente = criarCliente();
        Cliente retorno = clienteService.cadastrar(cliente);
        assertNotNull(retorno);

        Cliente cliente1 = criarCliente();
        Cliente retorno1 = clienteService.cadastrar(cliente1);
        assertNotNull(retorno1);

        Collection<Cliente> list = clienteService.listarTodos();
        assertTrue(list != null);
        assertTrue(list.size() == 2);

        list.forEach(cli -> {
            try {
                clienteService.excluir(cli.getId());
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

        Collection<Cliente> list1 = clienteService.listarTodos();
        assertTrue(list1 != null);
        assertTrue(list1.size() == 0);
    }

    private Cliente criarCliente() {
        Cliente cliente = new Cliente();
        cliente.setCpf(rd.nextLong());
        cliente.setNome("Rodrigo");
        cliente.setCidade("São Paulo");
        cliente.setEnd("End");
        cliente.setEstado("SP");
        cliente.setNumero(10);
        cliente.setTel(1199999999L);
        return cliente;
    }
}

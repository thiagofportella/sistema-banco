package br.com.cefet.banco.persistencia.bd;

import br.com.cefet.banco.negocio.Cliente;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;

import static org.junit.Assert.*;

public class ClienteDAOTest {

    @Mock
    private Connection conexaoMock;

    @InjectMocks
    private ClienteDAO clienteDAOComConexaoMock;

    private ClienteDAO clienteDAO;

    @Before
    public void setUp() {
        clienteDAOComConexaoMock = new ClienteDAO();
        MockitoAnnotations.initMocks(this);

        clienteDAO = new ClienteDAO();
    }

    @Test
    public void adicionaCliente_quandoInsere_deveEncontrarNaBase() {
        // preparação de dados
        Cliente c = new Cliente("Cliente Teste", "111.111.111-11", "Rua de Teste", "teste", "teste");

        // roteiro
        clienteDAO.adicionaCliente(c);

        // verificação
        Cliente clienteEncontrado = clienteDAO.getCliente(c.getUsuario());
        assertEquals(c.getCpf(), clienteEncontrado.getCpf());

        clienteDAO.remove(clienteEncontrado);
    }

    @Test
    public void adicionaCliente_quandoConexaoFechada_deveAbrirNovaConexao() {
        // preparação de dados
        Cliente c = new Cliente("Cliente Teste", "111.111.111-11", "Rua de Teste", "teste", "teste");

        // roteiro
        clienteDAO.adicionaCliente(c);

        // verificação
        Cliente clienteEncontrado = clienteDAO.getCliente(c.getUsuario());
        assertEquals(c.getCpf(), clienteEncontrado.getCpf());

        clienteDAO.remove(clienteEncontrado);
    }
}
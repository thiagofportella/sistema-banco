package br.com.cefet.banco.negocio;

import br.com.cefet.banco.persistencia.bd.ConnectionFactory;
import br.com.cefet.banco.persistencia.bd.ContaDAO;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class ContaTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalErr = System.err;

    @BeforeClass
    public static void clearDatabase() throws SQLException {
        Connection cnn = new ConnectionFactory().getConexao();
        PreparedStatement stmt = cnn.prepareStatement("DELETE FROM Funcionario WHERE 1 = 1; DELETE FROM Conta WHERE 1 = 1; DELETE FROM Cliente WHERE 1 = 1;");
        stmt.execute();
    }

    @Before
    public void setUp() throws SQLException {
        System.setOut(new PrintStream(outContent));
        Connection cnn = new ConnectionFactory().getConexao();
        PreparedStatement stmt = cnn.prepareStatement("DELETE FROM Funcionario WHERE 1 = 1; DELETE FROM Conta WHERE 1 = 1; DELETE FROM Cliente WHERE 1 = 1;");
        stmt.execute();
    }

    @After
    public void tearDown() throws SQLException {
        Connection cnn = new ConnectionFactory().getConexao();
        PreparedStatement stmt = cnn.prepareStatement("DELETE FROM Funcionario WHERE 1 = 1; DELETE FROM Conta WHERE 1 = 1; DELETE FROM Cliente WHERE 1 = 1;");
        stmt.execute();
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void getTotalDeContas() {
        ContaDAO contaDAO = new ContaDAO();
        int total = contaDAO.getListaContas().size();

        assertEquals(total, 0);
    }

    @Test
    public void transferir() {
        Conta cc = new ContaCorrente(0.0);
        Cliente titular = new Cliente("nome", "cpf", "endereco", "usuario", "senha");
        cc.setTitular(titular);
        cc.setSaldo(1000.0);

        Conta cc2 = new ContaCorrente(0.0);
        Cliente titular2 = new Cliente("nome2", "cpf2", "endereco2", "usuario2", "senha2");
        cc2.setTitular(titular2);
        cc2.setSaldo(1000.0);

        cc.transferir(cc2, 100.0);

        assertEquals(900.0, cc.getSaldo(), 0.0);
    }

    @Test
    public void transferir_quandoSemSaldo() {
        Conta cc = new ContaCorrente(0.0);
        Cliente titular = new Cliente("nome", "cpf", "endereco", "usuario", "senha");
        cc.setTitular(titular);
        cc.setSaldo(0.0);

        Conta cc2 = new ContaCorrente(0.0);
        Cliente titular2 = new Cliente("nome2", "cpf2", "endereco2", "usuario2", "senha2");
        cc2.setTitular(titular2);
        cc2.setSaldo(1000.0);

        cc.transferir(cc2, 100.0);

        assertTrue(outContent.toString().contains("Saldo insuficiente"));
    }
}
package br.com.cefet.banco.negocio;

import br.com.cefet.banco.persistencia.bd.ClienteDAO;
import br.com.cefet.banco.persistencia.bd.ConnectionFactory;
import br.com.cefet.banco.persistencia.bd.ContaDAO;
import br.com.cefet.banco.persistencia.bd.FuncionarioDAO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class BancoTest {

    private final Cliente cliente = new Cliente("Thiago", "cpf", "Rua dos Alfeneiros", "jamespll", "12345678");
    private final Conta contaCorrente = new ContaCorrente(100.0);
    private final Conta contaPoupanca = new ContaPoupanca(200.0);

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalErr = System.err;

    @InjectMocks
    private Banco banco;

    @Mock
    private Conta conta;

    @Before
    public void setUp() throws SQLException {
        MockitoAnnotations.initMocks(this);
        System.setOut(new PrintStream(outContent));
        Connection cnn = new ConnectionFactory().getConexao();
        PreparedStatement stmt = cnn.prepareStatement("DELETE FROM Funcionario WHERE 1 = 1; DELETE FROM Conta WHERE 1 = 1; DELETE FROM Cliente WHERE 1 = 1;");
        stmt.execute();
    }

    @After
    public void restoreStreams() throws SQLException {
        System.setOut(originalOut);
        Connection cnn = new ConnectionFactory().getConexao();
        PreparedStatement stmt = cnn.prepareStatement("DELETE FROM Funcionario WHERE 1 = 1; DELETE FROM Conta WHERE 1 = 1; DELETE FROM Cliente WHERE 1 = 1;");
        stmt.execute();
    }

    @Test
    public void testCalcularSaldoTotal() {
        // preparação
        contaCorrente.setSaldo(100);
        contaPoupanca.setSaldo(200);

        ClienteDAO clienteDAO = new ClienteDAO();
        ContaDAO contaDAO = new ContaDAO();

        clienteDAO.adicionaCliente(cliente);

        contaCorrente.setTitular(cliente);
        contaPoupanca.setTitular(cliente);

        contaDAO.adicionaConta(contaCorrente);
        contaDAO.adicionaConta(contaPoupanca);

        // roteiro
        double total = banco.calcularSaldoTotal();

       // verificação
        assertEquals(300.0, total, 0.0);
    }

    @Test
    public void testCalcularLimiteMaximo() {
        // preparação de dados
        Conta conta = new ContaCorrente(0);
        double saldo = 0;
        double limite = 100;
        double gastoTotalBanco = 0;
        double saldoTotalBanco = 0;
        int numeroClientes = 0;
        int numeroFuncionarios = 0;
        Banco banco = new Banco();

        // Roteiro
        double novoLimite = banco.calcularLimiteMaximo(conta, saldo, limite, gastoTotalBanco,
                saldoTotalBanco, numeroClientes, numeroFuncionarios);

        // verificação
        assertEquals(100, novoLimite, 0.0);
    }

    @Test
    public void caixaBranca_calcularLimiteMaximo_caminho0() {
        // i, A, B, R, 0
        Conta conta = new ContaCorrente(0);
        double saldo = -10;
        double limite = 344;
        Banco banco = new Banco();

        double novoLimite = banco.calcularLimiteMaximo(conta, saldo, limite,
                0, 0, 0, 0);
        assertEquals(limite, novoLimite, 0.0);
    }

    @Test
    public void caixaBranca_calcularLimiteMaximo_caminho_1() {
        // i, A, C, E, F, H, K, O, P, Q, O
        Conta conta = new ContaPoupanca(0);
        double saldo = 10;
        double limite = 100;
        double gastoTotalBanco = 200;
        double saldoTotalBanco = 50;
        int numeroClientes = 2;
        int numeroFuncionarios = 5;

        Banco banco = new Banco();
        double novoLimite = banco.calcularLimiteMaximo(conta, saldo, limite, gastoTotalBanco, saldoTotalBanco,
                numeroClientes, numeroFuncionarios);

        assertEquals(saldoTotalBanco/numeroClientes, novoLimite, 0.0);

    }

    @Test
    public void caixaBranca_calcularLimiteMaximo_caminho_2() {
        // i, A, C, E, F, H, K, O, P, Q, O
        Conta conta = new ContaCorrente(0);
        double saldo = 10000;
        double limite = 100;
        double gastoTotalBanco = 200;
        double saldoTotalBanco = 50;
        int numeroClientes = 2000;
        int numeroFuncionarios = 5;

        Banco banco = new Banco();
        double novoLimite = banco.calcularLimiteMaximo(conta, saldo, limite, gastoTotalBanco, saldoTotalBanco,
                numeroClientes, numeroFuncionarios);

        assertEquals(saldoTotalBanco/numeroClientes, novoLimite, 0.0);
    }

    @Test
    public void caixaBranca_calcularLimiteMaximo_caminho_3() {
        Conta conta = new ContaCorrente(0);
        double saldo = 100;
        double limite = 100;
        double gastoTotalBanco = 200;
        double saldoTotalBanco = 50;
        int numeroClientes = 20;
        int numeroFuncionarios = 5;

        Banco banco = new Banco();
        double novoLimite = banco.calcularLimiteMaximo(conta, saldo, limite, gastoTotalBanco, saldoTotalBanco,
                numeroClientes, numeroFuncionarios);

        assertEquals(saldoTotalBanco/numeroClientes, novoLimite, 0.0);
    }

    @Test
    public void testCalcularTotalDeGastos() {
        // preparação de dados
        Funcionario funcionario = new Gerente("Thiago", "Rua dos Alfeneiros", "cpf",
                "jamespll", "12345678", "asdad", 1000.02);

        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();


        funcionarioDAO.adicionaFuncionario(funcionario);

        Banco banco = new Banco();

        double totalDeGastos = banco.calcularTotalDeGastos();

        assertEquals(1000.02, totalDeGastos, 0.0);

        funcionarioDAO.remove(funcionario);
    }

    @Test
    public void imprimeListaDeFuncionarios() {
        Funcionario funcionario = new Gerente("Thiago", "Rua dos Alfeneiros", "cpf",
                "jamespll", "12345678", "asdad", 1000.02);

        funcionario.setEstado(EstadoFuncionario.EM_FERIAS);

        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();


        funcionarioDAO.adicionaFuncionario(funcionario);

        Banco banco = new Banco();
        banco.imprimeListaDeFuncionarios();

        assertTrue(outContent.toString().contains("Nome: Thiago"));

    }

    @Test
    public void atualizarContas_quandoUmaConta() {
        Cliente titular = new Cliente("nome", "cpf", "endereco", "usuario", "senha");
        ClienteDAO clienteDAO = new ClienteDAO();
        clienteDAO.adicionaCliente(titular);

        Conta cc = new ContaCorrente(0.0);
        cc.setTitular(titular);
        cc.setSaldo(1000.0);
        ContaDAO contaDAO = new ContaDAO();
        contaDAO.adicionaConta(cc);

        Banco banco = new Banco();
        banco.atualizarContas(10.0);

        assertEquals(980.0, cc.getSaldo(), 0.0);

//        contaDAO.remove(cc);
//        clienteDAO.remove(titular);
    }

    @Test
    public void atualizarContas_quandoDuasContas() {
        Cliente titular = new Cliente("nome", "cpf", "endereco", "usuario", "senha");
        ClienteDAO clienteDAO = new ClienteDAO();
        clienteDAO.adicionaCliente(titular);

        Conta cc = new ContaCorrente(0.0);
        cc.setTitular(titular);
        cc.setSaldo(1000.0);
        ContaDAO contaDAO = new ContaDAO();
        contaDAO.adicionaConta(cc);

        Conta cc2 = new ContaCorrente(0.0);
        cc2.setTitular(titular);
        cc2.setSaldo(1000.0);
        contaDAO.adicionaConta(cc2);

        Banco banco = new Banco();
        banco.atualizarContas(10.0);

        List<Conta> contas = banco.getContas();

        assertEquals(980.0, contas.get(0).getSaldo(), 0.0);

//        contaDAO.remove(cc);
//        contaDAO.remove(cc2);
//        clienteDAO.remove(titular);
    }
}

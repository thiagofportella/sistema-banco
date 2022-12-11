package br.com.cefet.banco.negocio;

import br.com.cefet.banco.persistencia.bd.ClienteDAO;
import br.com.cefet.banco.persistencia.bd.ContaDAO;
import br.com.cefet.banco.persistencia.bd.FuncionarioDAO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;

public class BancoTest {

    private final Cliente cliente = new Cliente("Thiago", "cpf", "Rua dos Alfeneiros", "jamespll", "12345678");
    private final Conta contaCorrente = new ContaCorrente(100.0);
    private final Conta contaPoupanca = new ContaPoupanca(200.0);

    @InjectMocks
    private Banco banco;

    @Mock
    private Conta conta;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
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

        // clean database
        clienteDAO.remove(cliente);
        contaDAO.remove(contaCorrente);
        contaDAO.remove(contaPoupanca);
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
}

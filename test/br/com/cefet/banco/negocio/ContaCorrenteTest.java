package br.com.cefet.banco.negocio;

import br.com.cefet.banco.exceptions.DepositoInvalidoException;
import br.com.cefet.banco.exceptions.SaldoInsuficienteException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class ContaCorrenteTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalErr = System.err;

    @Before
    public void setUp(){
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void imprimirResumo() {
        Conta cc = new ContaCorrente(0.0);
        Cliente titular = new Cliente("nome", "cpf", "endereco", "usuario", "senha");
        cc.setTitular(titular);
        cc.setSaldo(1000);

        cc.imprimirResumo();

//        assertTrue(errContent.toString().contains("Corrente"));
        assertTrue(outContent.toString().contains("nome"));
        assertTrue(outContent.toString().contains(String.valueOf(1000)));
    }

    @Test
    public void atualiza() {
        Conta cc = new ContaCorrente(0.0);
        Cliente titular = new Cliente("nome", "cpf", "endereco", "usuario", "senha");
        cc.setTitular(titular);
        cc.setSaldo(1000.0);

        cc.atualiza(10.0);

        assertEquals(980.0, cc.getSaldo(), 0.0);
    }

    @Test
    public void getTipoStr() {
        Conta cc = new ContaCorrente(0.0);
        Cliente titular = new Cliente("nome", "cpf", "endereco", "usuario", "senha");
        cc.setTitular(titular);

        assertEquals("Conta corrente", cc.getTipoStr());
    }

    @Test
    public void sacar_quandoSaldoDisponivel() throws SaldoInsuficienteException {
        Conta cc = new ContaCorrente(0.0);
        Cliente titular = new Cliente("nome", "cpf", "endereco", "usuario", "senha");
        cc.setTitular(titular);
        cc.setSaldo(1000.0);

        cc.sacar(100.0);
        assertEquals(900.0, cc.getSaldo(), 0.0);
    }

    @Test(expected = SaldoInsuficienteException.class)
    public void sacar_quandoSaldoIndisponivel() throws SaldoInsuficienteException {
        Conta cc = new ContaCorrente(0.0);
        Cliente titular = new Cliente("nome", "cpf", "endereco", "usuario", "senha");
        cc.setTitular(titular);

        cc.sacar(100.0);
    }

    @Test
    public void depositar() throws DepositoInvalidoException {
        Conta cc = new ContaCorrente(0.0);
        Cliente titular = new Cliente("nome", "cpf", "endereco", "usuario", "senha");
        cc.setTitular(titular);
        cc.setSaldo(1000.0);

        cc.depositar(100.0);

        assertEquals(1100.0, cc.getSaldo(), 0.0);
    }

    @Test(expected = DepositoInvalidoException.class)
    public void depositar_quandoInvalido() throws DepositoInvalidoException {
        Conta cc = new ContaCorrente(0.0);
        Cliente titular = new Cliente("nome", "cpf", "endereco", "usuario", "senha");
        cc.setTitular(titular);
        cc.setSaldo(1000.0);

        cc.depositar(-100.0);
    }

    @Test
    public void compareTo_menorQue() {
        Conta cc = new ContaCorrente(0.0);
        Cliente titular = new Cliente("nome", "cpf", "endereco", "usuario", "senha");
        cc.setTitular(titular);
        cc.setSaldo(900.0);

        Conta cc2 = new ContaCorrente(0.0);
        Cliente titular2 = new Cliente("nome2", "cpf2", "endereco2", "usuario2", "senha2");
        cc.setTitular(titular);
        cc.setSaldo(1000.0);

        assertEquals(-1, cc.compareTo(cc2), 0.0);
    }

    @Test
    public void compareTo_maiorQue() {
        Conta cc = new ContaCorrente(0.0);
        Cliente titular = new Cliente("nome", "cpf", "endereco", "usuario", "senha");
        cc.setTitular(titular);
        cc.setSaldo(1000.0);

        Conta cc2 = new ContaCorrente(0.0);
        Cliente titular2 = new Cliente("nome2", "cpf2", "endereco2", "usuario2", "senha2");
        cc.setTitular(titular);
        cc.setSaldo(900.0);

        assertEquals(1, cc.compareTo(cc2), 0.0);
    }

    @Test
    public void compareTo_Igual() {
        Conta cc = new ContaCorrente(0.0);
        Cliente titular = new Cliente("nome", "cpf", "endereco", "usuario", "senha");
        cc.setTitular(titular);
        cc.setSaldo(1000.0);

        Conta cc2 = new ContaCorrente(0.0);
        Cliente titular2 = new Cliente("nome2", "cpf2", "endereco2", "usuario2", "senha2");
        cc2.setTitular(titular2);
        cc2.setSaldo(1000.0);

        assertEquals(0, cc.compareTo(cc2), 0.0);
    }
}
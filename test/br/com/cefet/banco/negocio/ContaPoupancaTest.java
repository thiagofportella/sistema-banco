package br.com.cefet.banco.negocio;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class ContaPoupancaTest {

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
        Conta cp = new ContaPoupanca(0.0);
        Cliente titular = new Cliente("Thiago", "cpf", "endereco", "user", "senha");
        cp.setTitular(titular);
        cp.setSaldo(1000);

        cp.imprimirResumo();

        assertTrue(outContent.toString().contains("Thiago"));
        assertTrue(outContent.toString().contains(String.valueOf(1000)));
    }

    @Test
    public void atualiza() {
        Conta cp = new ContaPoupanca(0.0);
        Cliente titular = new Cliente("Thiago", "cpf", "endereco", "user", "senha");
        cp.setTitular(titular);
        cp.setSaldo(1000);

        cp.atualiza(100);
        assertEquals(900, cp.getSaldo(), 0.0);
    }
}
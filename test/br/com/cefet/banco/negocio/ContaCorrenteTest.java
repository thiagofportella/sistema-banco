package br.com.cefet.banco.negocio;

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
}
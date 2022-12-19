package br.com.cefet.banco.negocio;

import org.junit.Test;

import static org.junit.Assert.*;

public class EfetuaDepositoTest {

    @Test
    public void run() {
        ContaCorrente cc = new ContaCorrente(0.0);
        Cliente titular = new Cliente("nome", "cpf", "endereco", "usuario", "senha");
        cc.setTitular(titular);

        EfetuaDeposito ed = new EfetuaDeposito(cc, 100);

        ed.run();

        assertEquals(100.0, cc.getSaldo(), 0.0);
    }

    @Test
    public void getConta() {
        ContaCorrente cc = new ContaCorrente(0.0);
        Cliente titular = new Cliente("nome", "cpf", "endereco", "usuario", "senha");
        cc.setTitular(titular);

        EfetuaDeposito ed = new EfetuaDeposito(cc, 100);

        assertEquals(cc, ed.getConta());
    }
}
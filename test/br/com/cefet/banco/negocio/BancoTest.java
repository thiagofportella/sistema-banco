package br.com.cefet.banco.negocio;

import org.junit.Test;

import static org.junit.Assert.*;

public class BancoTest {

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
}
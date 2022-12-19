package br.com.cefet.banco.negocio;

import org.junit.Test;

import static org.junit.Assert.*;

public class ControleDeBonificacoesTest {

    @Test
    public void getTotalDeBonificacoes() {
        Funcionario gerente1 = new Gerente("Thiago", "Rua dos Alfeneiros", "cpf",
                "jamespll", "12345678", "asdad", 1000.00);

        Funcionario gerente2 = new Gerente("James", "Rua dos Alfeneiros", "cpf",
                "plljames", "12345678", "dasdas", 1000.00);

        ControleDeBonificacoes controle = new ControleDeBonificacoes();
        controle.registra(gerente1);
        controle.registra(gerente2);

        double total = controle.getTotalDeBonificacoes();
        assertEquals(400.0, total, 0.0);
    }
}
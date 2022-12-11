package br.com.cefet.banco.negocio;

import org.junit.Test;

import static org.junit.Assert.*;

public class CaixaTest {

    @Test
    public void getBonificacao() {
        Caixa caixa = new Caixa("Thiago", "Rua dos Alfeneiros", "cpf", "Caixa",
                "caixa", "caixa", 100.00);
        double bonificacao = caixa.getBonificacao();

        assertEquals(10, bonificacao, 0.0);
    }

    @Test
    public void getNumeroDoGuiche() {
        Caixa caixa = new Caixa("Thiago", "Rua dos Alfeneiros", "cpf", "Caixa",
                "caixa", "caixa", 100.00);
        caixa.setNumeroDoGuiche(1);
        int numeroGuiche = caixa.getNumeroDoGuiche();

        assertEquals(1, numeroGuiche, 0.0);
    }


    @Test
    public void getCargoStr() {
        Caixa caixa = new Caixa("Thiago", "Rua dos Alfeneiros", "cpf", "Caixa",
                "caixa", "caixa", 100.00);
        caixa.setCargo(0);

        assertEquals("Caixa", caixa.getCargoStr());
    }
}
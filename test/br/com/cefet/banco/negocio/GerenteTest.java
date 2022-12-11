package br.com.cefet.banco.negocio;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

public class GerenteTest {
    @Before
    public void setUp() throws Exception {
        Field totalGerentes = Gerente.class.getDeclaredField("totalGerentes");
        totalGerentes.setAccessible(true);
        totalGerentes.setInt(null, 0);
    }

    @Test
    public void getBonificacao() {
        Gerente gerente = new Gerente("Thiago", "Rua dos Alfeneiros", "cpf", "Gerente",
                "gerente", "gerente", 100.00);
        double bonificacao = gerente.getBonificacao();

        assertEquals(20, bonificacao, 0.0);
    }

    @Test
    public void getTotalGerentes() {
        Gerente gerente1 = new Gerente("Thiago", "Rua dos Alfeneiros", "cpf", "Gerente",
                "gerente1", "gerente1", 100.00);
        Gerente gerente2 = new Gerente("Thiago", "Rua dos Alfeneiros", "cpf", "Gerente",
                "gerente2", "gerente2", 100.00);

        int total = Gerente.getTotalGerentes();
        assertEquals(2, total, 0.0);
    }
}
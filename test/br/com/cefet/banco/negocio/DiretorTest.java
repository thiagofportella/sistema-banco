package br.com.cefet.banco.negocio;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DiretorTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void autenticarQuandoSenhaCorreta() {
        Diretor diretor = new Diretor("Thiago", "Rua dos Alfeneiros", "cpf", "Direção",
                "diretor", "diretor", 100.00);

        boolean auth = diretor.autenticar(diretor.getSenha());

        assertTrue(auth);
    }

    @Test
    public void autenticarQuandoSenhaIncorreta() {
        Diretor diretor = new Diretor("Thiago", "Rua dos Alfeneiros", "cpf", "Direção",
                "diretor", "diretor", 100.00);

        boolean auth = diretor.autenticar("foobar");

        assertFalse(auth);
    }

    @Test
    public void getBonificacao() {
        Diretor diretor = new Diretor("Thiago", "Rua dos Alfeneiros", "cpf", "Direção",
                "diretor", "diretor", 100.00);

        double bonificacao = diretor.getBonificacao();
        assertEquals(50.0, bonificacao, 0.0);
    }

    @Test
    public void getCargoStr() {
        Diretor diretor = new Diretor("Thiago", "Rua dos Alfeneiros", "cpf", "Direção",
                "diretor", "diretor", 100.00);
        diretor.setCargo(2);

        assertEquals("Diretor", diretor.getCargoStr());
    }
}
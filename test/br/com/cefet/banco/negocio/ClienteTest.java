package br.com.cefet.banco.negocio;

import org.junit.Test;

import static org.junit.Assert.*;

public class ClienteTest {

    @Test
    public void setCpf() {
        Cliente cliente = new Cliente("Thiago", "cpf", "Rua dos Alfeneiros", "jamespll",
                "12345678");
        cliente.setCpf("cpf");
        assertEquals("Invalido", cliente.getCpf());
    }

    @Test
    public void autenticarQuandoSenhaCorreta() {
        String senha = "12345678";
        Cliente cliente = new Cliente("Thiago", "cpf", "Rua dos Alfeneiros", "jamespll",
                senha);
        boolean auth = cliente.autenticar(senha);
        assertTrue(auth);
    }

    @Test
    public void autenticarQuandoSenhaIncorreta() {
        String senha = "12345678";
        Cliente cliente = new Cliente("Thiago", "cpf", "Rua dos Alfeneiros", "jamespll",
                senha);
        boolean auth = cliente.autenticar("1233");
        assertFalse(auth);
    }
}
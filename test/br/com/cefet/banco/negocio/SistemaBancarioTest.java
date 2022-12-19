package br.com.cefet.banco.negocio;

import org.junit.Test;

import static org.junit.Assert.*;

public class SistemaBancarioTest {

    @Test
    public void login() {
        Cliente titular = new Cliente("nome", "cpf", "endereco", "usuario", "senha");
        new SistemaBancario().login(titular);
    }
}
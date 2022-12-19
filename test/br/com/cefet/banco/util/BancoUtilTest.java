package br.com.cefet.banco.util;

import br.com.cefet.banco.negocio.*;
import br.com.cefet.banco.persistencia.bd.ClienteDAO;
import org.junit.Test;

import static org.junit.Assert.*;

public class BancoUtilTest {

    @Test
    public void pegarNomeUsuario_quandoCliente() {
        Cliente cliente = new Cliente("Thiago", "cpf", "Rua dos Alfeneiros", "jamespll",
                "12345678");

        String nomeUsuario = BancoUtil.pegarNomeUsuario(cliente);
        assertEquals(cliente.getNome(), nomeUsuario);
    }

    @Test
    public void pegarNomeUsuario_quandoFuncionario() {
        Funcionario funcionario = new Gerente("Thiago", "Rua dos Alfeneiros", "cpf",
                "jamespll", "12345678", "asdad", 1000.02);
        String nomeUsuario = BancoUtil.pegarNomeUsuario(funcionario);

        assertEquals(funcionario.getNome(), nomeUsuario);
    }

    @Test
    public void possuiFuncionalidadesCaixa_quandoCaixa() {
        Caixa caixa = new Caixa("Thiago", "Rua dos Alfeneiros", "cpf", "Caixa",
                "caixa", "caixa", 100.00);

        boolean permissao = BancoUtil.possuiFuncionalidadesCaixa(caixa);
        assertTrue(permissao);
    }

    @Test
    public void possuiFuncionalidadesCaixa_quandoDiretor() {
        Diretor diretor = new Diretor("Thiago", "Rua dos Alfeneiros", "cpf", "Direção",
                "diretor", "diretor", 100.00);

        boolean permissao = BancoUtil.possuiFuncionalidadesCaixa(diretor);
        assertTrue(permissao);
    }

    @Test
    public void possuiFuncionalidadesCaixa_quandoCliente() {
        Cliente cliente = new Cliente("Thiago", "cpf", "Rua dos Alfeneiros", "jamespll",
                "12345678");

        boolean permissao = BancoUtil.possuiFuncionalidadesCaixa(cliente);
        assertFalse(permissao);
    }

    @Test
    public void possuiFuncionalidadesGerente_quandoDiretor() {
        Diretor diretor = new Diretor("Thiago", "Rua dos Alfeneiros", "cpf", "Direção",
                "diretor", "diretor", 100.00);

        boolean permissao = BancoUtil.possuiFuncionalidadesGerente(diretor);
        assertTrue(permissao);
    }

    @Test
    public void possuiFuncionalidadesGerente_quandoCliente() {
        Cliente cliente = new Cliente("Thiago", "cpf", "Rua dos Alfeneiros", "jamespll",
                "12345678");

        boolean permissao = BancoUtil.possuiFuncionalidadesGerente(cliente);
        assertFalse(permissao);
    }

    @Test
    public void possuiFuncionalidadesDiretor_quandoDiretor() {
        Diretor diretor = new Diretor("Thiago", "Rua dos Alfeneiros", "cpf", "Direção",
                "diretor", "diretor", 100.00);

        boolean permissao = BancoUtil.possuiFuncionalidadesDiretor(diretor);
        assertTrue(permissao);
    }

    @Test
    public void possuiFuncionalidadesDiretor_quandoCaixa() {
        Caixa caixa = new Caixa("Thiago", "Rua dos Alfeneiros", "cpf", "Caixa",
                "caixa", "caixa", 100.00);

        boolean permissao = BancoUtil.possuiFuncionalidadesDiretor(caixa);
        assertFalse(permissao);
    }

    @Test
    public void realizarLogin_quandoUsuarioExiste() {
        Cliente cliente = new Cliente("Thiago", "cpf", "Rua dos Alfeneiros", "jamespll",
                "12345678");
        ClienteDAO clienteDAO = new ClienteDAO();
        clienteDAO.adicionaCliente(cliente);

        Autenticavel login =  BancoUtil.realizarLogin(cliente.getUsuario(), cliente.getSenha());

        assert login != null;
        assertEquals(cliente.getUsuario(), login.getUsuario());

        clienteDAO.remove(cliente);
    }

    @Test
    public void realizarLogin_quandoUsuarioEhNulo() {
        Autenticavel login =  BancoUtil.realizarLogin("asd", "asd");

        assertNull(login);
    }

    @Test
    public void converteSenha() {
        char[] senha = new char[2];
        senha[0] = 'a';
        senha[1] = 'b';

        assertEquals("ab", BancoUtil.converteSenha(senha));

    }
}
package br.com.cefet.banco.negocio;

import br.com.cefet.banco.exceptions.AutoPromocaoFuncionarioException;
import br.com.cefet.banco.exceptions.PromocaoFuncionarioCargoInvalidoException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PromoverFuncionarioTest {
    private final Gerente gerente = new Gerente("Thiago", "Rua dos Alfeneiros", "cpf", "Gerente",
            "gerente", "gerente", 100.00);

    private final Caixa caixa = new Caixa("Thiago", "Rua dos Alfeneiros", "cpf", "Caixa",
                                                                "caixa", "caixa", 100.00);

    private final Diretor diretor = new Diretor("Thiago", "Rua dos Alfeneiros", "cpf", "Direção",
            "diretor", "diretor", 100.00);


    @Test(expected = AutoPromocaoFuncionarioException.class)
    public void promoverFuncionario_quandoAutoPromocao() throws AutoPromocaoFuncionarioException, PromocaoFuncionarioCargoInvalidoException {
        gerente.promoverFuncionario(gerente, 1);
    }

    @Test(expected = PromocaoFuncionarioCargoInvalidoException.class)
    public void promoverFuncionario_quandoSuperior() throws AutoPromocaoFuncionarioException, PromocaoFuncionarioCargoInvalidoException {
        caixa.promoverFuncionario(gerente, 2);
    }

    @Test(expected = PromocaoFuncionarioCargoInvalidoException.class)
    public void promoverFuncionario_quandoMesmoCargo() throws AutoPromocaoFuncionarioException, PromocaoFuncionarioCargoInvalidoException {
        Caixa outroCaixa = new Caixa("Thiago2", "Rua dos Alfeneiros2", "cpf2", "Caixa2",
                "caixa2", "caixa2", 102.00);
        caixa.promoverFuncionario(outroCaixa, 2);
    }

    @Test
    public void promoverFuncionario_happyPath() throws AutoPromocaoFuncionarioException, PromocaoFuncionarioCargoInvalidoException {
        diretor.promoverFuncionario(caixa, 1);
        assertEquals(caixa.getCargoStr(), "Gerente");
    }
}

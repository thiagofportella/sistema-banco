package br.com.cefet.banco.exceptions;

import java.io.IOException;

public class PromocaoFuncionarioCargoInvalidoException extends IOException {
    public PromocaoFuncionarioCargoInvalidoException(String message) {
        super(message);
    }
}

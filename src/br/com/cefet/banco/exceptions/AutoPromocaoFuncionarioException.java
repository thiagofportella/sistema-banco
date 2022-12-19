package br.com.cefet.banco.exceptions;

import java.io.IOException;

public class AutoPromocaoFuncionarioException extends IOException {
    public AutoPromocaoFuncionarioException(String message) {
        super(message);
    }
}

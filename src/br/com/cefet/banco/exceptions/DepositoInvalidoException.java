package br.com.cefet.banco.exceptions;

import java.io.IOException;

public class DepositoInvalidoException extends IOException {

	public DepositoInvalidoException(String message){
		super(message);
	}

}

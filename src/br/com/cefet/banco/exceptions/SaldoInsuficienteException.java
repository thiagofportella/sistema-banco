package br.com.cefet.banco.exceptions;

public class SaldoInsuficienteException extends Exception{
	public SaldoInsuficienteException(String message){
		super(message);
	}
}

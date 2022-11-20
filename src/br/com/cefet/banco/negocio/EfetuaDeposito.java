package br.com.cefet.banco.negocio;

import br.com.cefet.banco.exceptions.DepositoInvalidoException;

public class EfetuaDeposito implements Runnable{
	
	private final double deposito;
	private final ContaCorrente conta;
	
	public EfetuaDeposito(ContaCorrente cc, double valor) {
		this.deposito = valor;
		this.conta = cc;
	}
	
	public void run() {
		try {
			System.out.println("Thread EfetuaDeposito rodando...");
			conta.depositar(deposito);
		} catch (DepositoInvalidoException e) {
			System.out.println(e.getMessage());
		}
	}

	public ContaCorrente getConta() {
		return conta;
	}
	
	
	
}



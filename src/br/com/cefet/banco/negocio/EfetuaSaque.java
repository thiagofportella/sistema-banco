package br.com.cefet.banco.negocio;

import br.com.cefet.banco.exceptions.DepositoInvalidoException;
import br.com.cefet.banco.exceptions.SaldoInsuficienteException;

public class EfetuaSaque extends Thread{
	
	private ContaCorrente conta;
	private double valor;
	
	public EfetuaSaque(ContaCorrente c, double v){
		this.conta = c;
		this.valor = v;
	}
	
	public void run() {
		try {
			System.out.println("Thread EfetuaSaque rodando...");
			conta.sacar(valor);
			System.out.println("Saque efetuado!");
		} catch (SaldoInsuficienteException e) {
			System.err.println(e.getMessage());
		}
	}

	public ContaCorrente getConta() {
		return conta;
	}

	
	
}

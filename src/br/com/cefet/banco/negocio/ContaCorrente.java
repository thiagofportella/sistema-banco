package br.com.cefet.banco.negocio;

import br.com.cefet.banco.exceptions.DepositoInvalidoException;

public class ContaCorrente extends Conta {

	public ContaCorrente(double saldoInicial) {
		super(saldoInicial);
		setTipo(0);
	}
	
	public ContaCorrente(double saldoInicial, double limite) {
		super(saldoInicial, limite);
		setTipo(0);
	}
	

	public void depositar(double valor) throws DepositoInvalidoException {
		super.depositar(valor);
//		this.saldo-=1;
	}

	public void atualiza(double taxa) {
		synchronized (this){
			this.saldo = this.saldo - 2*taxa; 
		}
	}
	
	public void imprimirResumo() {
		System.err.println("---- Conta Corrente ---");
		super.imprimirResumo();
	}
}


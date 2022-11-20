package br.com.cefet.banco.negocio;

public class ContaPoupanca extends Conta {

	public ContaPoupanca(double saldoInicial) {
		super(saldoInicial);
		setTipo(1);
	}
	
	public ContaPoupanca(double saldoInicial, double limite) {
		super(saldoInicial, limite);
		setTipo(1);
	}


	public void imprimirResumo() {
		System.err.println("---- Conta Poupança ----");
		super.imprimirResumo();
	}


	@Override
	public void atualiza(double taxa) {
		synchronized (this){
			this.saldo=this.saldo-taxa; 	
		}
	}

}

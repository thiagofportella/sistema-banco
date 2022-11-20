package br.com.cefet.banco.negocio;

public class AtualizadorDeContas {
	private double saldoTotal = 0;
	private double selic;
	
	public AtualizadorDeContas(double selic){
		this.selic = selic;
	}
	
	public void roda(Conta c) {
		// aqui vc imprime o saldo anterior,
		System.out.println("Saldo anterior: " + c.getSaldo());
		// atualiza a conta
		c.atualiza(this.selic);
		// e depois imprime o saldo final
		System.out.println("Saldo atual: " + c.getSaldo());
		// lembrando de somar o saldo final ao atributo saldoTotal
		this.saldoTotal += c.getSaldo();
	}

	public double getSaldoTotal() {
		return saldoTotal;
	}

	public double getSelic() {
		return selic;
	}
	
	
}

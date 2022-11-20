package br.com.cefet.banco.negocio;

public class ControleDeBonificacoes {
	private double totalDeBonificacoes = 0;
	
	public double getTotalDeBonificacoes(){
		return this.totalDeBonificacoes;
	}
	
	public void registra(Funcionario funcionario){
		this.totalDeBonificacoes += funcionario.getBonificacao();
	}
}



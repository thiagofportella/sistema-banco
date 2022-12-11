package br.com.cefet.banco.negocio;

public class Gerente extends Funcionario {
	
	// atributos e métodos
	
	private static int totalGerentes;
	
	public Gerente(String n, String e, String c, String d, String s, String u, double sal){
		super(n, e, c, d, s, u, sal);
		this.setCargo(1);
		Gerente.totalGerentes++;
	}
	
	public double getBonificacao(){
		return this.salario * 0.20;
	}

	public static int getTotalGerentes() {
		return totalGerentes;
	}
	
	
	
}

package br.com.cefet.banco.negocio;

public class Caixa extends Funcionario {

	private int numeroDoGuiche;
	
	public Caixa(String n, String e, String c, String d, String s, String u, double sal){
		super(n, e, c, d, s, u, sal);
		this.setCargo(0);
	}
	
	public int getNumeroDoGuiche(){
		return numeroDoGuiche;
	}
	
	public void setNumeroDoGuiche(int numeroDoGuiche) {
		this.numeroDoGuiche = numeroDoGuiche;
	}

	@Override
	public double getBonificacao() {
		return this.salario * 0.10;
	}

}


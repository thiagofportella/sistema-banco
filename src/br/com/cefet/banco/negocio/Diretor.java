package br.com.cefet.banco.negocio;

public class Diretor extends Funcionario {

	private int senha;
	
	public Diretor(String n, String e, String c, String d, String s, String u, double sal){
		super(n, e, c, d, s, u, sal);
		this.setCargo(2);
	}
	
	public boolean autenticar(int senha){
		if(this.senha==senha){
			System.out.println("Acesso permitido!");
			return true;
		} else {
			return false;
		}
	}
	

	@Override
	public double getBonificacao() {
		return this.salario * 0.05;
	}

}

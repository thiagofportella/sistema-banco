package br.com.cefet.banco.negocio;

public class SistemaBancario {
	private String senha = "123456";
	
	public void login(Autenticavel a){
		a.autenticar(this.senha);
	}
}



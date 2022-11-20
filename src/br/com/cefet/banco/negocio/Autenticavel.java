package br.com.cefet.banco.negocio;

public interface Autenticavel {
	boolean autenticar(String senha);
	String getUsuario();
}



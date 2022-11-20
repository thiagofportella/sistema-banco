package br.com.cefet.banco.util;

import br.com.cefet.banco.negocio.Autenticavel;
import br.com.cefet.banco.negocio.Caixa;
import br.com.cefet.banco.negocio.Cliente;
import br.com.cefet.banco.negocio.Diretor;
import br.com.cefet.banco.negocio.Funcionario;
import br.com.cefet.banco.negocio.Gerente;
import br.com.cefet.banco.persistencia.bd.ClienteDAO;
import br.com.cefet.banco.persistencia.bd.FuncionarioDAO;

public class BancoUtil {
	public static String pegarNomeUsuario(Autenticavel a) {
		if(a instanceof Cliente) {
			return ((Cliente) a).getNome();
		} else {
			return ((Funcionario) a).getNome();
		}
	}
	
	public static boolean usuarioEhCliente(Autenticavel u) {
		return u instanceof Cliente;
	}
	
	public static boolean usuarioEhCaixa(Autenticavel u) {
		return u instanceof Caixa;
	}
	
	public static boolean usuarioEhGerente(Autenticavel u) {
		return u instanceof Gerente;
	}
	
	public static boolean usuarioEhDiretor(Autenticavel u) {
		return u instanceof Diretor;
	}
	
	public static boolean possuiFuncionalidadesCaixa(Autenticavel u) {
		if(usuarioEhCaixa(u) || usuarioEhGerente(u) || usuarioEhDiretor(u)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean possuiFuncionalidadesGerente(Autenticavel u) {
		if(usuarioEhGerente(u) || usuarioEhDiretor(u)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean possuiFuncionalidadesDiretor(Autenticavel u) {
		if(usuarioEhDiretor(u)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static Autenticavel realizarLogin(String username, String senha) {
		Autenticavel usuario;
		ClienteDAO cdao = new ClienteDAO();
		usuario = cdao.getCliente(username);
		if(usuario == null) {
			FuncionarioDAO fdao = new FuncionarioDAO();
			usuario = fdao.getFuncionario(username);
		}		
		if(usuario!= null && usuario.autenticar(senha)) {
			return usuario;
		} else {
			return null;
		}
	}
	
	public static String converteSenha(char[] senha) {
		String s = "";
		for(char c: senha) {
			s += c;
		}
		return s;
	}
}

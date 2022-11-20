package br.com.cefet.banco.negocio;

public class Cliente implements Autenticavel {
	private long id;
	private String nome;
	private String endereco;
	private String cpf;
	private String usuario;
	private String senha;
	
	public Cliente(String n, String c, String e, String u, String s){
		this.nome = n;
		this.cpf = validaCpf(c)?c:"Invalido";
		this.endereco = e;
		this.usuario = u;
		this.senha = s;
	}
	

	public String getNome(){
		return this.nome;
	}
	
	public static boolean validaCpf(String cpf){
		if (cpf.length() != 14){
			return false;
		} else {
			return true;
		}
	}
	
	public void setCpf(String c){
		this.cpf = validaCpf(c)?c:"Invalido";
	}
	
	public String getCpf(){
		return this.cpf;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}


	@Override
	public boolean autenticar(String senha){
		if(this.senha.compareTo(senha)==0){
			System.out.println("Acesso permitido!");
			return true;
		} else {
			return false;
		}
		
	}
	
	

}

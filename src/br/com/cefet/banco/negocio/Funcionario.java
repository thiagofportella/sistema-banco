package br.com.cefet.banco.negocio;

import br.com.cefet.banco.exceptions.AutoPromocaoFuncionarioException;
import br.com.cefet.banco.exceptions.PromocaoFuncionarioCargoInvalidoException;

/**
 * @author marco
 *
 */
public abstract class Funcionario implements Autenticavel{
	private long id; // id do banco de dados
	
	private String nome;
	private String endereco;
	private String cpf;
	private String departamento;
	private String senha;
	private String usuario;
	protected double salario;
	private int cargo; // 0 - Caixa, 1 - Gerente, 2 - Diretor
	
	private final int identificador;
	private static int totalFuncionarios;
	
	private EstadoFuncionario estado;
	
	public Funcionario() {this.identificador = Funcionario.totalFuncionarios;}
	
	public Funcionario(String n, String e, String c, String d, String s, String u, double sal){
		Funcionario.totalFuncionarios++;
		this.identificador = Funcionario.totalFuncionarios;
		this.nome = n;
		this.endereco = e;
		this.cpf = c;
		this.departamento = d;
		this.senha = s;
		this.usuario = u;
		this.salario = sal;		
	}
	
	public abstract double getBonificacao();
	
//	public double getBonificacao(){
//		return this.salario * 0.10;
//	}
		
	public boolean aumentarSalario(int porcentagem){
		if (porcentagem > 0){
			this.salario += this.salario*porcentagem/100;
			return true;
		} else {
			return false;
		}
	}
	
	public void imprimirResumo(){
		System.out.println("-----------------------");
		System.out.println("Nome: "+this.nome);
		System.out.println("Endereço: "+this.endereco);
		System.out.println("CPF: "+this.cpf);
		System.out.println("Departamento: "+this.departamento);
		System.out.println("Salário: "+this.salario);
		System.out.println("Estado: "+this.estado.toString());
	}
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getDepartamento() {
		return departamento;
	}
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public double getSalario() {
		return salario;
	}
	public void setSalario(double salario) {
		this.salario = salario;
	}

	public int getIdentificador() {
		return identificador;
	}

	public EstadoFuncionario getEstado() {
		return estado;
	}

	public void setEstado(EstadoFuncionario estado) {
		this.estado = estado;
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

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public int getCargo() {
		return cargo;
	}
	
	public String getCargoStr() {
		if(cargo==0) {
			return "Caixa";
		} else if(cargo == 1) {
			return "Gerente";
		} else if(cargo == 2) {
			return "Diretor";
		} else {
			return "Cargo inválido";
		}

	}

	public void setCargo(int cargo) {
		this.cargo = cargo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public static int getTotalFuncionarios() {
		return totalFuncionarios;
	}

	public void promoverFuncionario(Funcionario funcionario, int cargo) throws AutoPromocaoFuncionarioException, PromocaoFuncionarioCargoInvalidoException {
		if (this.equals(funcionario)) {
			throw new AutoPromocaoFuncionarioException("Funcionário não pode se autopromover!");
		}

		if (funcionario.getCargo() >= this.cargo) {
			throw new PromocaoFuncionarioCargoInvalidoException("Cargo inválido!");
		}

		funcionario.setCargo(cargo);
	}

}

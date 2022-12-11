package br.com.cefet.banco.persistencia.bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.naming.CommunicationException;

import br.com.cefet.banco.negocio.Caixa;
import br.com.cefet.banco.negocio.Cliente;
import br.com.cefet.banco.negocio.Diretor;
import br.com.cefet.banco.negocio.Funcionario;
import br.com.cefet.banco.negocio.Gerente;

public class FuncionarioDAO {
	private Connection conexao;
	private String comandoSQL;
	private PreparedStatement stmt;

	public FuncionarioDAO(){
		this.conexao = new ConnectionFactory().getConexao();
	}

	public void adicionaFuncionario(Funcionario f){
		// Testando se preciso fabricar uma conex�o
		try{
			if(conexao.isClosed()){
				conexao = new ConnectionFactory().getConexao();
			}
		} catch (SQLException e){
			System.out.println("Erro ao obter conex�o. " + e.getMessage());
		}

		// Declarar minha instru��o sql
		this.comandoSQL = "INSERT INTO funcionario(nome,endereco, cpf, usuario, senha, departamento, cargo, salario) VALUES (?,?,?,?,?,?,?,?)";

		try {
			// Preparando a stmt
			this.stmt = this.conexao.prepareStatement(comandoSQL,PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setString(1, f.getNome());
			stmt.setString(2, f.getEndereco());
			stmt.setString(3, f.getCpf());
			stmt.setString(4, f.getUsuario());
			stmt.setString(5, f.getSenha());
			stmt.setString(6, f.getDepartamento());
			stmt.setInt(7, f.getCargo());
			stmt.setDouble(8, f.getSalario());

			// Executa a stmt preparada
			stmt.execute();
			
			try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
				    f.setId(generatedKeys.getLong(1));
				}else {
				    throw new SQLException("Falha a inserção, nenhuma ID obtida.");
				}
			}

			System.out.println("Funcionario "+f.getNome()+" adicionado com sucesso com a id "+f.getId()+"!");

		} catch (SQLException e) {
			System.out.println("Erro ao adicionar. " + e.getMessage());
		} finally {
			// liberando os recursos
			try {
				stmt.close();
				conexao.close();
				System.out.println(conexao);
			} catch (SQLException e) {
				System.out.println("Não foi possível liberar os recursos. " + e.getMessage());
			}
		}
	}


	public List<Funcionario> getListaFuncionarios(){
		// Testando se preciso fabricar uma conex�o
		try{
			if(conexao.isClosed()){
				conexao = new ConnectionFactory().getConexao();
			}
		} catch (SQLException e){
			System.out.println("Erro ao obter conexão. " + e.getMessage());
		}

		this.comandoSQL = "SELECT * FROM Funcionario";
		List<Funcionario> funcionarios = new ArrayList<Funcionario>();

		try {
			this.stmt = conexao.prepareStatement(comandoSQL);
			// Obtenho os dados e guardo em um ResultSet
			ResultSet rs = stmt.executeQuery();
			Funcionario f;
			while (rs.next()){
				int cargo = rs.getInt("cargo");
				switch (cargo) {
				case 1:
					f = new Gerente(rs.getString("nome"), 
									rs.getString("endereco"), 
									rs.getString("cpf"), 
									rs.getString("departamento"), 
									rs.getString("senha"), 
									rs.getString("usuario"), 
									rs.getDouble("salario"));
					break;
				case 2:
					f = new Diretor(rs.getString("nome"), 
							rs.getString("endereco"), 
							rs.getString("cpf"), 
							rs.getString("departamento"), 
							rs.getString("senha"), 
							rs.getString("usuario"), 
							rs.getDouble("salario"));
					break;
				default:
					f = new Caixa(rs.getString("nome"), 
							rs.getString("endereco"), 
							rs.getString("cpf"), 
							rs.getString("departamento"), 
							rs.getString("senha"), 
							rs.getString("usuario"), 
							rs.getDouble("salario"));
					break;
				}
				f.setId(rs.getLong("idFuncionario"));
				funcionarios.add(f);
			}
			return funcionarios;
		} catch (SQLException e) {
			System.out.println("Não foi possível listar. " + e.getMessage());
			return null;
		}finally {
			// liberando os recursos
			try {
				stmt.close();
				conexao.close();
				System.out.println(conexao);
			} catch (SQLException e) {
				System.out.println("Não foi possível liberar os recursos. " + e.getMessage());
			}
		}
	}
	
	public int getFuncionarioCount(){
		try{
			if(conexao.isClosed()){
				conexao = new ConnectionFactory().getConexao();
			}
		} catch (SQLException e){
			System.out.println("Erro ao obter conexão. " + e.getMessage());
		}

		this.comandoSQL = "SELECT COUNT(*) FROM Funcionario";

		try {
			this.stmt = conexao.prepareStatement(comandoSQL);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			int count = rs.getInt(1);
			return count;
		} catch (SQLException e) {
			System.out.println("Não foi possível listar. " + e.getMessage());
			return 0;
		} finally {
			// liberando os recursos
			try {
				stmt.close();
				conexao.close();
				System.out.println(conexao);
			} catch (SQLException e) {
				System.out.println("Não foi possível liberar os recursos. " + e.getMessage());
			}
		}
	}
	
	public void altera(Funcionario f){
		// Testando se preciso fabricar uma conex�o
		try{
			if(conexao.isClosed()){
				conexao = new ConnectionFactory().getConexao();
			}
		} catch (SQLException e){
			System.out.println("Erro ao obter conexão. " + e.getMessage());
		}

		// Declarar minha instru��o sql
		this.comandoSQL = "UPDATE Funcionario SET nome=?,endereco=?,cpf=?,usuario=?,senha=?,departamento=?,cargo=?,salario=? WHERE idFuncionario=?";
		try {
			// Preparando a stmt
			this.stmt = this.conexao.prepareStatement(comandoSQL);
			stmt.setString(1, f.getNome());
			stmt.setString(2, f.getEndereco());
			stmt.setString(3, f.getCpf());
			stmt.setString(4, f.getUsuario());
			stmt.setString(5, f.getSenha());
			stmt.setString(6, f.getDepartamento());
			stmt.setInt(7, f.getCargo());
			stmt.setDouble(8, f.getSalario());
			stmt.setLong(9, f.getId());

			// Executa a stmt preparada
			stmt.execute();

			System.out.println("Funcionario "+f.getNome()+" alterado com sucesso!");

		} catch (SQLException e) {
			System.out.println("Erro ao alterar. " + e.getMessage());
		} finally {
			// liberando os recursos
			try {
				stmt.close();
				conexao.close();
				System.out.println(conexao);
			} catch (SQLException e) {
				System.out.println("Não foi possível liberar os recursos. " + e.getMessage());
			}
		}
	}
	
	
	
	public void remove(Funcionario f){
		// Testando se preciso fabricar uma conex�o
		try{
			if(conexao.isClosed()){
				conexao = new ConnectionFactory().getConexao();
			}
		} catch (SQLException e){
			System.out.println("Erro ao obter conexão. " + e.getMessage());
		}

		// Declarar minha instru��o sql
		this.comandoSQL = "DELETE FROM Funcionario WHERE idFuncionario=?";

		try {
			// Preparando a stmt
			this.stmt = this.conexao.prepareStatement(comandoSQL);
			stmt.setLong(1, f.getId());

			// Executa a stmt preparada
			stmt.execute();

			System.out.println("Funcionario "+f.getNome()+" removido com sucesso!");

		} catch (SQLException e) {
			System.out.println("Erro ao remover. " + e.getMessage());
		} finally {
			// liberando os recursos
			try {
				stmt.close();
				conexao.close();
				System.out.println(conexao);
			} catch (SQLException e) {
				System.out.println("N�o foi poss�vel liberar os recursos. " + e.getMessage());
			}
		}
	}

	public Funcionario getFuncionario(long id){
		// Testando se preciso fabricar uma conex�o
		try{
			if(conexao.isClosed()){
				conexao = new ConnectionFactory().getConexao();
			}
		} catch (SQLException e){
			System.out.println("Erro ao obter conex�o. " + e.getMessage());
		}

		this.comandoSQL = "SELECT * FROM Funcionario WHERE idFuncionario = ?";
		Funcionario f = null;

		try {
			this.stmt = conexao.prepareStatement(comandoSQL);
			stmt.setLong(1, id);
			
			// Obtenho os dados e guardo em um ResultSet
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				int cargo = rs.getInt("cargo");
				switch (cargo) {
				case 1:
					f = new Gerente(rs.getString("nome"), 
									rs.getString("endereco"), 
									rs.getString("cpf"), 
									rs.getString("departamento"), 
									rs.getString("senha"), 
									rs.getString("usuario"), 
									rs.getDouble("salario"));
					break;
				case 2:
					f = new Diretor(rs.getString("nome"), 
							rs.getString("endereco"), 
							rs.getString("cpf"), 
							rs.getString("departamento"), 
							rs.getString("senha"), 
							rs.getString("usuario"), 
							rs.getDouble("salario"));
					break;
				default:
					f = new Caixa(rs.getString("nome"), 
							rs.getString("endereco"), 
							rs.getString("cpf"), 
							rs.getString("departamento"), 
							rs.getString("senha"), 
							rs.getString("usuario"), 
							rs.getDouble("salario"));
					break;
				}
				f.setId(id);
			}
			
			return f;
		} catch (SQLException e) {
			System.out.println("N�o foi poss�vel listar. " + e.getMessage());
			return null;
		}finally {
			// liberando os recursos
			try {
				stmt.close();
				conexao.close();
				System.out.println(conexao);
			} catch (SQLException e) {
				System.out.println("N�o foi poss�vel liberar os recursos. " + e.getMessage());
			}
		}
	}
	
	public Funcionario getFuncionario(String usuario){
		// Testando se preciso fabricar uma conex�o
		try{
			if(conexao.isClosed()){
				conexao = new ConnectionFactory().getConexao();
			}
		} catch (SQLException e){
			System.out.println("Erro ao obter conex�o. " + e.getMessage());
		}

		this.comandoSQL = "SELECT * FROM Funcionario WHERE usuario = ?";
		Funcionario f = null;

		try {
			this.stmt = conexao.prepareStatement(comandoSQL);
			stmt.setString(1, usuario);
			
			// Obtenho os dados e guardo em um ResultSet
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				int cargo = rs.getInt("cargo");
				switch (cargo) {
				case 1:
					f = new Gerente(rs.getString("nome"), 
									rs.getString("endereco"), 
									rs.getString("cpf"), 
									rs.getString("departamento"), 
									rs.getString("senha"), 
									rs.getString("usuario"), 
									rs.getDouble("salario"));
					break;
				case 2:
					f = new Diretor(rs.getString("nome"), 
							rs.getString("endereco"), 
							rs.getString("cpf"), 
							rs.getString("departamento"), 
							rs.getString("senha"), 
							rs.getString("usuario"), 
							rs.getDouble("salario"));
					break;
				default:
					f = new Caixa(rs.getString("nome"), 
							rs.getString("endereco"), 
							rs.getString("cpf"), 
							rs.getString("departamento"), 
							rs.getString("senha"), 
							rs.getString("usuario"), 
							rs.getDouble("salario"));
					break;
				}
				f.setId(rs.getLong("idFuncionario"));
			}
			
			return f;
		} catch (SQLException e) {
			System.out.println("N�o foi poss�vel listar. " + e.getMessage());
			return null;
		}finally {
			// liberando os recursos
			try {
				stmt.close();
				conexao.close();
				System.out.println(conexao);
			} catch (SQLException e) {
				System.out.println("N�o foi poss�vel liberar os recursos. " + e.getMessage());
			}
		}
	}
	
	public static java.sql.Date calendarParaSQLDate(Calendar dataEmCalendar){
		java.sql.Date dataSQL = new java.sql.Date(dataEmCalendar.getTimeInMillis());
		return dataSQL;
	}

	public static java.util.Calendar sqlDateParaCalendar(java.sql.Date sqlDate){
		Calendar dataEmCalendar = Calendar.getInstance();
		dataEmCalendar.setTime(sqlDate);
		return dataEmCalendar;
	}

}



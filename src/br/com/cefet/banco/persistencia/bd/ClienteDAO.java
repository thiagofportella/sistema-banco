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

public class ClienteDAO {
	private Connection conexao;
	private String comandoSQL;
	private PreparedStatement stmt;

	public ClienteDAO(){
		this.conexao = new ConnectionFactory().getConexao();
	}

	public void adicionaCliente(Cliente c){
		// Testando se preciso fabricar uma conex�o
		try{
			if(conexao.isClosed()){
				conexao = new ConnectionFactory().getConexao();
			}
		} catch (SQLException e){
			System.out.println("Erro ao obter conex�o. " + e.getMessage());
		}

		// Declarar minha instru��o sql
		this.comandoSQL = "INSERT INTO cliente(nome,endereco, cpf, usuario, senha) VALUES (?,?,?,?,?)";

		try {
			// Preparando a stmt
			this.stmt = this.conexao.prepareStatement(comandoSQL,PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setString(1, c.getNome());
			stmt.setString(2, c.getEndereco());
			stmt.setString(3, c.getCpf());
			stmt.setString(4, c.getUsuario());
			stmt.setString(5, c.getSenha());

			// Executa a stmt preparada
			stmt.execute();
			
			try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
				    c.setId(generatedKeys.getLong(1));
				}else {
				    throw new SQLException("Falha a inser��o, nenhuma ID obtida.");
				}
			}

			System.out.println("Cliente "+c.getNome()+" adicionado com sucesso com a id "+c.getId()+"!");

		} catch (SQLException e) {
			System.out.println("Erro ao adicionar. " + e.getMessage());
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


	public List<Cliente> getListaClientes(){
		// Testando se preciso fabricar uma conex�o
		try{
			if(conexao.isClosed()){
				conexao = new ConnectionFactory().getConexao();
			}
		} catch (SQLException e){
			System.out.println("Erro ao obter conex�o. " + e.getMessage());
		}

		this.comandoSQL = "SELECT * FROM Cliente";
		List<Cliente> clientes = new ArrayList<Cliente>();

		try {
			this.stmt = conexao.prepareStatement(comandoSQL);
			// Obtenho os dados e guardo em um ResultSet
			ResultSet rs = stmt.executeQuery();
			Cliente c;
			while (rs.next()){
				c = new Cliente(rs.getString("nome"), 
								rs.getString("endereco"), 
								rs.getString("cpf"), 
								rs.getString("usuario"), 
								rs.getString("senha"));
				c.setId(rs.getLong("idCliente"));
				clientes.add(c);
			}
			return clientes;
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
	
	public Cliente getCliente(long id){
		// Testando se preciso fabricar uma conexão
		try{
			if(conexao.isClosed()){
				conexao = new ConnectionFactory().getConexao();
			}
		} catch (SQLException e){
			System.out.println("Erro ao obter conexão. " + e.getMessage());
		}

		this.comandoSQL = "SELECT * FROM Cliente WHERE idCliente = ?";
		Cliente cliente = null;

		try {
			this.stmt = conexao.prepareStatement(comandoSQL);
			stmt.setLong(1, id);
			
			// Obtenho os dados e guardo em um ResultSet
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				cliente = new Cliente(rs.getString("nome"), 
								rs.getString("cpf"), 
								rs.getString("endereco"), 
								rs.getString("usuario"), 
								rs.getString("senha"));
				cliente.setId(id);
			}
			
			return cliente;
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
	
	public void altera(Cliente c){
		// Testando se preciso fabricar uma conex�o
		try{
			if(conexao.isClosed()){
				conexao = new ConnectionFactory().getConexao();
			}
		} catch (SQLException e){
			System.out.println("Erro ao obter conexão. " + e.getMessage());
		}

		// Declarar minha instru��o sql
		this.comandoSQL = "UPDATE Cliente SET nome=?,endereco=?,cpf=?,usuario=?,senha=? WHERE idCliente=?";
		try {
			// Preparando a stmt
			this.stmt = this.conexao.prepareStatement(comandoSQL);
			stmt.setString(1, c.getNome());
			stmt.setString(2, c.getEndereco());
			stmt.setString(3, c.getCpf());
			stmt.setString(4, c.getUsuario());
			stmt.setString(5, c.getSenha());
			stmt.setLong(6, c.getId());

			// Executa a stmt preparada
			stmt.execute();

			System.out.println("Funcionario "+c.getNome()+" alterado com sucesso!");

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
	
	
	
	public void remove(Cliente c){
		// Testando se preciso fabricar uma conexão
		try{
			if(conexao.isClosed()){
				conexao = new ConnectionFactory().getConexao();
			}
		} catch (SQLException e){
			System.out.println("Erro ao obter conexão. " + e.getMessage());
		}

		// Declarar minha instrução sql
		this.comandoSQL = "DELETE FROM Cliente WHERE idCliente=?";

		try {
			// Preparando a stmt
			this.stmt = this.conexao.prepareStatement(comandoSQL);
			stmt.setLong(1, c.getId());

			// Executa a stmt preparada
			stmt.execute();

			System.out.println("Funcionario "+c.getNome()+" removido com sucesso!");

		} catch (SQLException e) {
			System.out.println("Erro ao remover. " + e.getMessage());
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


	public static java.sql.Date calendarParaSQLDate(Calendar dataEmCalendar){
		java.sql.Date dataSQL = new java.sql.Date(dataEmCalendar.getTimeInMillis());
		return dataSQL;
	}

	public static java.util.Calendar sqlDateParaCalendar(java.sql.Date sqlDate){
		Calendar dataEmCalendar = Calendar.getInstance();
		dataEmCalendar.setTime(sqlDate);
		return dataEmCalendar;
	}

	
	public Cliente getCliente(String usuario){
		// Testando se preciso fabricar uma conexão
		try{
			if(conexao.isClosed()){
				conexao = new ConnectionFactory().getConexao();
			}
		} catch (SQLException e){
			System.out.println("Erro ao obter conexão. " + e.getMessage());
		}

		this.comandoSQL = "SELECT * FROM Cliente WHERE usuario LIKE ?";
		Cliente cliente = null;

		try {
			this.stmt = conexao.prepareStatement(comandoSQL);
			stmt.setString(1, usuario);
			
			// Obtenho os dados e guardo em um ResultSet
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				cliente = new Cliente(rs.getString("nome"), 
								rs.getString("cpf"), 
								rs.getString("endereco"), 
								rs.getString("usuario"), 
								rs.getString("senha"));
				cliente.setId(rs.getLong("idCliente"));
			}
			
			return cliente;
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
	
}



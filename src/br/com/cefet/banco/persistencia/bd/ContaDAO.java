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
import br.com.cefet.banco.negocio.Conta;
import br.com.cefet.banco.negocio.ContaCorrente;
import br.com.cefet.banco.negocio.ContaPoupanca;
import br.com.cefet.banco.negocio.Diretor;
import br.com.cefet.banco.negocio.Funcionario;
import br.com.cefet.banco.negocio.Gerente;

public class ContaDAO {
	private Connection conexao;
	private String comandoSQL;
	private PreparedStatement stmt;
	private ClienteDAO clienteDAO;

	public ContaDAO(){
		this.conexao = new ConnectionFactory().getConexao();
		this.clienteDAO = new ClienteDAO();
	}

	public void adicionaConta(Conta conta){
		// Testando se preciso fabricar uma conexão
		try{
			if(conexao.isClosed()){
				conexao = new ConnectionFactory().getConexao();
			}
		} catch (SQLException e){
			System.out.println("Erro ao obter conexão. " + e.getMessage());
		}

		// Declarar minha instrução sql
		this.comandoSQL = "INSERT INTO Conta(saldo, limite, tipo, Cliente_idCliente) VALUES (?,?,?,?)";

		try {
			// Preparando a stmt
			this.stmt = this.conexao.prepareStatement(comandoSQL,PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setDouble(1, conta.getSaldo());
			stmt.setDouble(2, conta.getLimite());
			stmt.setInt(3, conta.getTipo());
			stmt.setLong(4, conta.getTitular().getId());

			// Executa a stmt preparada
			stmt.execute();
			
			try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					conta.setId(generatedKeys.getLong(1));
				}else {
				    throw new SQLException("Falha a inserção, nenhuma ID obtida.");
				}
			}

			System.out.println("Conta com saldo "+conta.getSaldo()+" adicionado com sucesso com a id "+conta.getId()+" para o cliente "+conta.getTitular().getNome()+"!");

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


	public List<Conta> getListaContas(){
		// Testando se preciso fabricar uma conexão
		try{
			if(conexao.isClosed()){
				conexao = new ConnectionFactory().getConexao();
			}
		} catch (SQLException e){
			System.out.println("Erro ao obter conexão. " + e.getMessage());
		}

		this.comandoSQL = "SELECT * FROM Conta";
		List<Conta> contas = new ArrayList<Conta>();

		try {
			this.stmt = conexao.prepareStatement(comandoSQL);
			// Obtenho os dados e guardo em um ResultSet
			ResultSet rs = stmt.executeQuery();
			Conta c = null;
			while (rs.next()){
				int tipo = rs.getInt("tipo");
				switch (tipo) {
				case 0:
					c = new ContaCorrente(rs.getDouble("saldo"), 
							rs.getDouble("limite"));
					break;
				case 1:
					c = new ContaPoupanca(rs.getDouble("saldo"), 
							rs.getDouble("limite"));
					break;
				default:
					break;
				}
				c.setId(rs.getLong("idConta"));
				Cliente titular = clienteDAO.getCliente(rs.getLong("Cliente_idCliente"));
				c.setTitular(titular);
				contas.add(c);
			}
			return contas;
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
	
	public void altera(Conta c){
		// Testando se preciso fabricar uma conexão
		try{
			if(conexao.isClosed()){
				conexao = new ConnectionFactory().getConexao();
			}
		} catch (SQLException e){
			System.out.println("Erro ao obter conexão. " + e.getMessage());
		}

		// Declarar minha instrução sql
		this.comandoSQL = "UPDATE Conta SET saldo=?,limite=? WHERE idConta=?";
		try {
			// Preparando a stmt
			this.stmt = this.conexao.prepareStatement(comandoSQL);
			stmt.setDouble(1, c.getSaldo());
			stmt.setDouble(2, c.getLimite());
			stmt.setLong(3, c.getId());

			// Executa a stmt preparada
			stmt.execute();

			System.out.println("Conta do cliente "+c.getTitular().getNome()+" alterada com sucesso!");

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
	
	
	
	public void remove(Conta c){
		// Testando se preciso fabricar uma conexão
		try{
			if(conexao.isClosed()){
				conexao = new ConnectionFactory().getConexao();
			}
		} catch (SQLException e){
			System.out.println("Erro ao obter conexão. " + e.getMessage());
		}

		// Declarar minha instrução sql
		this.comandoSQL = "DELETE FROM Conta WHERE idConta=?";

		try {
			// Preparando a stmt
			this.stmt = this.conexao.prepareStatement(comandoSQL);
			stmt.setLong(1, c.getId());

			// Executa a stmt preparada
			stmt.execute();

			System.out.println("Conta do cliente "+c.getTitular().getNome()+" removida com sucesso!");

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

	public Conta getConta(long id){
		// Testando se preciso fabricar uma conexão
		try{
			if(conexao.isClosed()){
				conexao = new ConnectionFactory().getConexao();
			}
		} catch (SQLException e){
			System.out.println("Erro ao obter conexão. " + e.getMessage());
		}

		this.comandoSQL = "SELECT * FROM Conta WHERE idConta = ?";
		Conta c = null;

		try {
			this.stmt = conexao.prepareStatement(comandoSQL);
			stmt.setLong(1, id);
			
			// Obtenho os dados e guardo em um ResultSet
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				int tipo = rs.getInt("tipo");
				switch (tipo) {
				case 0:
					c = new ContaCorrente(rs.getDouble("saldo"), 
							rs.getDouble("limite"));
					break;
				case 1:
					c = new ContaPoupanca(rs.getDouble("saldo"), 
							rs.getDouble("limite"));
					break;
				default:
					break;
				}
				c.setId(id);
				Cliente titular = clienteDAO.getCliente(rs.getLong("Cliente_idCliente"));
				c.setTitular(titular);
			}
			return c;
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
	
	public Conta getContaDeCliente(Cliente cliente){
		// Testando se preciso fabricar uma conexão
		try{
			if(conexao.isClosed()){
				conexao = new ConnectionFactory().getConexao();
			}
		} catch (SQLException e){
			System.out.println("Erro ao obter conexão. " + e.getMessage());
		}

		this.comandoSQL = "SELECT * FROM Conta WHERE Cliente_idCliente = ?";
		Conta c = null;

		try {
			this.stmt = conexao.prepareStatement(comandoSQL);
			stmt.setLong(1, cliente.getId());
			
			// Obtenho os dados e guardo em um ResultSet
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				int tipo = rs.getInt("tipo");
				switch (tipo) {
				case 0:
					c = new ContaCorrente(rs.getDouble("saldo"), 
							rs.getDouble("limite"));
					break;
				case 1:
					c = new ContaPoupanca(rs.getDouble("saldo"), 
							rs.getDouble("limite"));
					break;
				default:
					break;
				}
				c.setId(rs.getLong("idConta"));
				Cliente titular = clienteDAO.getCliente(rs.getLong("Cliente_idCliente"));
				c.setTitular(titular);
			}
			return c;
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



package br.com.cefet.banco.persistencia.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	public Connection getConexao(){
		try{
			System.out.println("Conectando com o banco...");
			return DriverManager.getConnection("jdbc:postgresql://localhost/banco","postgres","");
		} catch (SQLException e){
			System.err.println("Erro na conexão!");
			throw new RuntimeException(e);
		}
	}
}



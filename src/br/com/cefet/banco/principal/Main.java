package br.com.cefet.banco.principal;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import br.com.cefet.banco.apresentacao.TelaLogin;
import br.com.cefet.banco.apresentacao.TelaPrimeiroDiretor;
import br.com.cefet.banco.persistencia.bd.FuncionarioDAO;

public class Main {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FuncionarioDAO fdao = new FuncionarioDAO();
					if(fdao.getFuncionarioCount()==0) {
						TelaPrimeiroDiretor window = new TelaPrimeiroDiretor();
						window.frame.setVisible(true);
					} else {
						TelaLogin window = new TelaLogin();
						window.frmLogin.setVisible(true);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}
}

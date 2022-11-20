	package br.com.cefet.banco.apresentacao;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import javax.swing.SwingConstants;

import br.com.cefet.banco.negocio.Autenticavel;
import br.com.cefet.banco.negocio.Cliente;
import br.com.cefet.banco.negocio.Funcionario;
import br.com.cefet.banco.persistencia.bd.ClienteDAO;
import br.com.cefet.banco.persistencia.bd.FuncionarioDAO;
import br.com.cefet.banco.util.BancoUtil;

import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;

import java.awt.Dialog.ModalExclusionType;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.TitledBorder;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Font;
import javax.swing.SpringLayout;

public class TelaLogin implements ActionListener {

	public JFrame frmLogin;
	
	// Textos
	JPasswordField jpfPassword;
	JTextField jtfUsername;

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


	public TelaLogin() {
		initialize();
	}

	private void initialize() {
		frmLogin = new JFrame();
		frmLogin.setType(Type.POPUP);
		frmLogin.setResizable(false);
		frmLogin.setTitle("Acesso");
		frmLogin.setAlwaysOnTop(true);
		frmLogin.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		frmLogin.setBounds(100, 100, 321, 180);
		frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frmLogin.setLocation(dim.width/2-frmLogin.getSize().width/2, dim.height/2-frmLogin.getSize().height/2);

		JPanel bordaPnl = new JPanel();
		bordaPnl.setBorder(new TitledBorder(null, "Login", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		SpringLayout sl_bordaPnl = new SpringLayout();
		bordaPnl.setLayout(sl_bordaPnl);
		SpringLayout springLayout = new SpringLayout();
		springLayout.putConstraint(SpringLayout.NORTH, bordaPnl, 10, SpringLayout.NORTH, frmLogin.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, bordaPnl, 10, SpringLayout.WEST, frmLogin.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, bordaPnl, -10, SpringLayout.SOUTH, frmLogin.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, bordaPnl, -10, SpringLayout.EAST, frmLogin.getContentPane());
		frmLogin.getContentPane().setLayout(springLayout);

		

		
		frmLogin.getContentPane().add(bordaPnl);
		
				JLabel jlblUsername = new JLabel("Usu\u00E1rio");
				sl_bordaPnl.putConstraint(SpringLayout.NORTH, jlblUsername, 7, SpringLayout.NORTH, bordaPnl);
				sl_bordaPnl.putConstraint(SpringLayout.WEST, jlblUsername, 10, SpringLayout.WEST, bordaPnl);
				bordaPnl.add(jlblUsername);
				jlblUsername.setFont(new Font("Arial", Font.PLAIN, 15));
				jlblUsername.setHorizontalAlignment(SwingConstants.CENTER);
				
				
				jlblUsername.setVerticalAlignment(SwingConstants.CENTER);
				jtfUsername = new JTextField(15);
				sl_bordaPnl.putConstraint(SpringLayout.NORTH, jtfUsername, 5, SpringLayout.NORTH, bordaPnl);
				sl_bordaPnl.putConstraint(SpringLayout.WEST, jtfUsername, 20, SpringLayout.EAST, jlblUsername);
				springLayout.putConstraint(SpringLayout.NORTH, jtfUsername, 0, SpringLayout.NORTH, bordaPnl);
				springLayout.putConstraint(SpringLayout.WEST, jtfUsername, 406, SpringLayout.WEST, bordaPnl);
				springLayout.putConstraint(SpringLayout.EAST, jtfUsername, -10, SpringLayout.EAST, bordaPnl);
				bordaPnl.add(jtfUsername);
				
						JLabel jlblPassword = new JLabel("Senha");
						sl_bordaPnl.putConstraint(SpringLayout.NORTH, jlblPassword, 14, SpringLayout.SOUTH, jlblUsername);
						sl_bordaPnl.putConstraint(SpringLayout.EAST, jlblPassword, 0, SpringLayout.EAST, jlblUsername);
						bordaPnl.add(jlblPassword);
						jlblPassword.setFont(new Font("Arial", Font.PLAIN, 15));
						jlblPassword.setHorizontalAlignment(SwingConstants.CENTER);
						jpfPassword = new JPasswordField();
						sl_bordaPnl.putConstraint(SpringLayout.EAST, jtfUsername, 0, SpringLayout.EAST, jpfPassword);
						sl_bordaPnl.putConstraint(SpringLayout.NORTH, jpfPassword, 37, SpringLayout.NORTH, bordaPnl);
						sl_bordaPnl.putConstraint(SpringLayout.WEST, jpfPassword, 20, SpringLayout.EAST, jlblPassword);
						sl_bordaPnl.putConstraint(SpringLayout.EAST, jpfPassword, -20, SpringLayout.EAST, bordaPnl);
						bordaPnl.add(jpfPassword);
						jpfPassword.setColumns(15);
						
								////////////////////////////////////////////////////
								////////////////////////////////////////////////////
						
								JButton jbtOk = new JButton("Entrar");
								sl_bordaPnl.putConstraint(SpringLayout.NORTH, jbtOk, 10, SpringLayout.SOUTH, jpfPassword);
								sl_bordaPnl.putConstraint(SpringLayout.WEST, jbtOk, 60, SpringLayout.WEST, bordaPnl);
								bordaPnl.add(jbtOk);
								jbtOk.setFont(new Font("Arial", Font.PLAIN, 15));
								jbtOk.setAlignmentX(Component.CENTER_ALIGNMENT);
								
										jbtOk.addActionListener(this);
										
												jbtOk.setActionCommand("Login");
												JButton jbtCancel = new JButton("Cancelar");
												sl_bordaPnl.putConstraint(SpringLayout.NORTH, jbtCancel, 0, SpringLayout.NORTH, jbtOk);
												sl_bordaPnl.putConstraint(SpringLayout.WEST, jbtCancel, 6, SpringLayout.EAST, jbtOk);
												bordaPnl.add(jbtCancel);
												jbtCancel.setFont(new Font("Arial", Font.PLAIN, 15));
												jbtCancel.setHorizontalTextPosition(SwingConstants.CENTER);
												jbtCancel.setAlignmentX(Component.CENTER_ALIGNMENT);
												jbtCancel.addActionListener(this);
												jbtCancel.setActionCommand("Cancelar");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if ("Login".equals(e.getActionCommand())) {
			Autenticavel usuario = BancoUtil.realizarLogin(jtfUsername.getText(),BancoUtil.converteSenha(jpfPassword.getPassword()));
			if(usuario!= null) {
				this.frmLogin.dispose();
				TelaPrincipal window = new TelaPrincipal(usuario);
				window.frmSistemaBancrio.setVisible(true);
			} else {
				// Acesso negado!
				JOptionPane.showMessageDialog(frmLogin, "Acesso negado!", "Erro", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			this.frmLogin.dispose();
		}

	}

}

package br.com.cefet.banco.apresentacao;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import br.com.cefet.banco.negocio.Autenticavel;
import br.com.cefet.banco.negocio.Cliente;
import br.com.cefet.banco.negocio.Funcionario;
import br.com.cefet.banco.persistencia.bd.ClienteDAO;
import br.com.cefet.banco.persistencia.bd.FuncionarioDAO;
import br.com.cefet.banco.util.BancoUtil;

import javax.swing.UIManager;
import java.awt.Color;

public class TelaAlterarSenha  implements ActionListener {

	JFrame frmLogin;
	
	Autenticavel usuarioLogado;
	
	// Textos
	JPasswordField jpfSenhaNova;
	JPasswordField jtfSenhaAntiga;

	public TelaAlterarSenha(Autenticavel a) {
		this.usuarioLogado = a;
		initialize();
	}

	private void initialize() {
		frmLogin = new JFrame();
		frmLogin.setType(Type.POPUP);
		frmLogin.setResizable(false);
		frmLogin.setTitle("Acesso ao sistema");
		frmLogin.setAlwaysOnTop(true);
		frmLogin.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		frmLogin.setBounds(100, 100, 321, 180);
		frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frmLogin.setLocation(dim.width/2-frmLogin.getSize().width/2, dim.height/2-frmLogin.getSize().height/2);

		JPanel bordaPnl = new JPanel();
		bordaPnl.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Alterar senha", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		SpringLayout sl_bordaPnl = new SpringLayout();
		bordaPnl.setLayout(sl_bordaPnl);
		SpringLayout springLayout = new SpringLayout();
		springLayout.putConstraint(SpringLayout.NORTH, bordaPnl, 10, SpringLayout.NORTH, frmLogin.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, bordaPnl, 10, SpringLayout.WEST, frmLogin.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, bordaPnl, -10, SpringLayout.SOUTH, frmLogin.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, bordaPnl, -10, SpringLayout.EAST, frmLogin.getContentPane());
		frmLogin.getContentPane().setLayout(springLayout);

		

		
		frmLogin.getContentPane().add(bordaPnl);
		
				JLabel jlblUsername = new JLabel("Senha antiga");
				sl_bordaPnl.putConstraint(SpringLayout.WEST, jlblUsername, 10, SpringLayout.WEST, bordaPnl);
				bordaPnl.add(jlblUsername);
				jlblUsername.setFont(new Font("Arial", Font.PLAIN, 15));
				jlblUsername.setHorizontalAlignment(SwingConstants.CENTER);
				
				
				jlblUsername.setVerticalAlignment(SwingConstants.CENTER);
				jtfSenhaAntiga = new JPasswordField(15);
				sl_bordaPnl.putConstraint(SpringLayout.NORTH, jtfSenhaAntiga, 5, SpringLayout.NORTH, bordaPnl);
				sl_bordaPnl.putConstraint(SpringLayout.WEST, jtfSenhaAntiga, 20, SpringLayout.EAST, jlblUsername);
				sl_bordaPnl.putConstraint(SpringLayout.NORTH, jlblUsername, 2, SpringLayout.NORTH, jtfSenhaAntiga);
				springLayout.putConstraint(SpringLayout.NORTH, jtfSenhaAntiga, 0, SpringLayout.NORTH, bordaPnl);
				springLayout.putConstraint(SpringLayout.WEST, jtfSenhaAntiga, 406, SpringLayout.WEST, bordaPnl);
				springLayout.putConstraint(SpringLayout.EAST, jtfSenhaAntiga, -10, SpringLayout.EAST, bordaPnl);
				bordaPnl.add(jtfSenhaAntiga);
				
						JLabel jlblPassword = new JLabel("Nova senha");
						sl_bordaPnl.putConstraint(SpringLayout.EAST, jlblPassword, 0, SpringLayout.EAST, jlblUsername);
						bordaPnl.add(jlblPassword);
						jlblPassword.setFont(new Font("Arial", Font.PLAIN, 15));
						jlblPassword.setHorizontalAlignment(SwingConstants.CENTER);
						jpfSenhaNova = new JPasswordField();
						sl_bordaPnl.putConstraint(SpringLayout.NORTH, jlblPassword, 2, SpringLayout.NORTH, jpfSenhaNova);
						sl_bordaPnl.putConstraint(SpringLayout.NORTH, jpfSenhaNova, 10, SpringLayout.SOUTH, jtfSenhaAntiga);
						sl_bordaPnl.putConstraint(SpringLayout.WEST, jpfSenhaNova, 0, SpringLayout.WEST, jtfSenhaAntiga);
						sl_bordaPnl.putConstraint(SpringLayout.EAST, jpfSenhaNova, 0, SpringLayout.EAST, jtfSenhaAntiga);
						bordaPnl.add(jpfSenhaNova);
						jpfSenhaNova.setColumns(15);
						
						
								JButton jbtOk = new JButton("Salvar");
								sl_bordaPnl.putConstraint(SpringLayout.NORTH, jbtOk, 10, SpringLayout.SOUTH, jpfSenhaNova);
								sl_bordaPnl.putConstraint(SpringLayout.WEST, jbtOk, 60, SpringLayout.WEST, bordaPnl);
								bordaPnl.add(jbtOk);
								jbtOk.setFont(new Font("Arial", Font.PLAIN, 15));
								jbtOk.setAlignmentX(Component.CENTER_ALIGNMENT);
								
										jbtOk.addActionListener(this);
										
												jbtOk.setActionCommand("Salvar");
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
		if ("Salvar".equals(e.getActionCommand())) {
			String novaSenha = BancoUtil.converteSenha(jpfSenhaNova.getPassword());
			Autenticavel usuario;
			ClienteDAO cdao = new ClienteDAO();
			usuario = cdao.getCliente(usuarioLogado.getUsuario());
			if(usuario == null) {
				FuncionarioDAO fdao = new FuncionarioDAO();
				usuario = fdao.getFuncionario(usuarioLogado.getUsuario());
			}		
			if(usuario!= null && usuario.autenticar(BancoUtil.converteSenha(jtfSenhaAntiga.getPassword()))) {
				if(usuario instanceof Cliente) {
					Cliente clienteLogado = (Cliente) usuario;
					clienteLogado.setSenha(novaSenha);
					cdao.altera(clienteLogado);
				} else {
					Funcionario funcionarioLogado = (Funcionario) usuario;
					funcionarioLogado.setSenha(novaSenha);
					FuncionarioDAO fdao = new FuncionarioDAO();
					fdao.altera(funcionarioLogado);
				}
				JOptionPane.showMessageDialog(frmLogin, "Senha alterado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
				this.frmLogin.dispose();
			} else {
				// Acesso negado!
				JOptionPane.showMessageDialog(frmLogin, "Senha antiga incorreta!", "Erro", JOptionPane.ERROR_MESSAGE);
			}
			
		} else {
			this.frmLogin.dispose();
		}

	}

	public Autenticavel getUsuarioLogado() {
		return usuarioLogado;
	}

	
	
}


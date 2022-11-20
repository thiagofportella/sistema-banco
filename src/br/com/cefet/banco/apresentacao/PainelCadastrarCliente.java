package br.com.cefet.banco.apresentacao;

import javax.swing.JPanel;
import javax.swing.JPasswordField;

import java.awt.BorderLayout;
import javax.swing.border.TitledBorder;

import br.com.cefet.banco.negocio.Cliente;
import br.com.cefet.banco.negocio.Conta;
import br.com.cefet.banco.negocio.ContaCorrente;
import br.com.cefet.banco.negocio.ContaPoupanca;
import br.com.cefet.banco.persistencia.bd.ClienteDAO;
import br.com.cefet.banco.persistencia.bd.ContaDAO;
import br.com.cefet.banco.util.BancoUtil;

import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import java.awt.Dimension;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;

public class PainelCadastrarCliente extends JPanel implements ActionListener {
	private JTextField nomeTxt;
	private JTextField enderecoTxt;
	private JTextField cpfTxt;
	private JTextField usuarioTxt;
	private JPasswordField senhaTxt;
	private JRadioButton rdbtnContaCorrente;
	private JRadioButton rdbtnContaPoupanca;
	private JPasswordField repetirSenhaTxt;

	/**
	 * Create the panel.
	 */
	public PainelCadastrarCliente() {
		setMinimumSize(new Dimension(500, 400));
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Novo Cliente", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panel);
		SpringLayout sl_panel = new SpringLayout();
		panel.setLayout(sl_panel);
		
		JLabel lblNome = new JLabel("Nome");
		sl_panel.putConstraint(SpringLayout.WEST, lblNome, 61, SpringLayout.WEST, panel);
		lblNome.setFont(new Font("Arial", Font.PLAIN, 20));
		panel.add(lblNome);
		
		JLabel lblEndereo = new JLabel("Endere\u00E7o");
		sl_panel.putConstraint(SpringLayout.WEST, lblEndereo, 0, SpringLayout.WEST, lblNome);
		lblEndereo.setFont(new Font("Arial", Font.PLAIN, 20));
		panel.add(lblEndereo);
		
		JLabel lblCpf = new JLabel("CPF");
		sl_panel.putConstraint(SpringLayout.WEST, lblCpf, 0, SpringLayout.WEST, lblNome);
		lblCpf.setFont(new Font("Arial", Font.PLAIN, 20));
		panel.add(lblCpf);
		
		JLabel lblUsurio = new JLabel("Usu\u00E1rio");
		sl_panel.putConstraint(SpringLayout.WEST, lblUsurio, 0, SpringLayout.WEST, lblNome);
		lblUsurio.setFont(new Font("Arial", Font.PLAIN, 20));
		panel.add(lblUsurio);
		
		JLabel lblSenha = new JLabel("Senha");
		sl_panel.putConstraint(SpringLayout.WEST, lblSenha, 0, SpringLayout.WEST, lblNome);
		lblSenha.setFont(new Font("Arial", Font.PLAIN, 20));
		panel.add(lblSenha);
		
		nomeTxt = new JTextField();
		sl_panel.putConstraint(SpringLayout.NORTH, nomeTxt, 40, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.WEST, nomeTxt, 61, SpringLayout.EAST, lblNome);
		sl_panel.putConstraint(SpringLayout.EAST, nomeTxt, -61, SpringLayout.EAST, panel);
		sl_panel.putConstraint(SpringLayout.NORTH, lblNome, 3, SpringLayout.NORTH, nomeTxt);
		nomeTxt.setFont(new Font("Arial", Font.PLAIN, 20));
		panel.add(nomeTxt);
		nomeTxt.setColumns(10);
		
		enderecoTxt = new JTextField();
		sl_panel.putConstraint(SpringLayout.NORTH, enderecoTxt, 30, SpringLayout.SOUTH, nomeTxt);
		sl_panel.putConstraint(SpringLayout.NORTH, lblEndereo, 3, SpringLayout.NORTH, enderecoTxt);
		sl_panel.putConstraint(SpringLayout.WEST, enderecoTxt, 0, SpringLayout.WEST, nomeTxt);
		sl_panel.putConstraint(SpringLayout.EAST, enderecoTxt, 0, SpringLayout.EAST, nomeTxt);
		enderecoTxt.setFont(new Font("Arial", Font.PLAIN, 20));
		enderecoTxt.setColumns(10);
		panel.add(enderecoTxt);
		
		cpfTxt = new JTextField();
		sl_panel.putConstraint(SpringLayout.NORTH, cpfTxt, 30, SpringLayout.SOUTH, enderecoTxt);
		sl_panel.putConstraint(SpringLayout.NORTH, lblCpf, 3, SpringLayout.NORTH, cpfTxt);
		sl_panel.putConstraint(SpringLayout.WEST, cpfTxt, 0, SpringLayout.WEST, nomeTxt);
		sl_panel.putConstraint(SpringLayout.EAST, cpfTxt, 0, SpringLayout.EAST, nomeTxt);
		cpfTxt.setFont(new Font("Arial", Font.PLAIN, 20));
		cpfTxt.setColumns(10);
		panel.add(cpfTxt);
		
		usuarioTxt = new JTextField();
		sl_panel.putConstraint(SpringLayout.NORTH, lblUsurio, 3, SpringLayout.NORTH, usuarioTxt);
		sl_panel.putConstraint(SpringLayout.NORTH, usuarioTxt, 30, SpringLayout.SOUTH, cpfTxt);
		sl_panel.putConstraint(SpringLayout.WEST, usuarioTxt, 0, SpringLayout.WEST, nomeTxt);
		sl_panel.putConstraint(SpringLayout.EAST, usuarioTxt, 0, SpringLayout.EAST, nomeTxt);
		usuarioTxt.setFont(new Font("Arial", Font.PLAIN, 20));
		usuarioTxt.setColumns(10);
		panel.add(usuarioTxt);
		
		senhaTxt = new JPasswordField();
		sl_panel.putConstraint(SpringLayout.NORTH, senhaTxt, 30, SpringLayout.SOUTH, usuarioTxt);
		sl_panel.putConstraint(SpringLayout.NORTH, lblSenha, 3, SpringLayout.NORTH, senhaTxt);
		sl_panel.putConstraint(SpringLayout.WEST, senhaTxt, 0, SpringLayout.WEST, nomeTxt);
		sl_panel.putConstraint(SpringLayout.EAST, senhaTxt, 0, SpringLayout.EAST, nomeTxt);
		senhaTxt.setFont(new Font("Arial", Font.PLAIN, 20));
		senhaTxt.setColumns(10);
		panel.add(senhaTxt);
		
		rdbtnContaCorrente = new JRadioButton("Conta corrente");
		sl_panel.putConstraint(SpringLayout.WEST, rdbtnContaCorrente, 0, SpringLayout.WEST, lblNome);
		rdbtnContaCorrente.setSelected(true);
		rdbtnContaCorrente.setFont(new Font("Arial", Font.PLAIN, 20));
		panel.add(rdbtnContaCorrente);
		
		rdbtnContaPoupanca = new JRadioButton("Conta poupan\u00E7a");
		sl_panel.putConstraint(SpringLayout.NORTH, rdbtnContaPoupanca, 16, SpringLayout.SOUTH, rdbtnContaCorrente);
		sl_panel.putConstraint(SpringLayout.WEST, rdbtnContaPoupanca, 0, SpringLayout.WEST, lblNome);
		rdbtnContaPoupanca.setFont(new Font("Arial", Font.PLAIN, 20));
		panel.add(rdbtnContaPoupanca);
		
		ButtonGroup group = new ButtonGroup();
	    group.add(rdbtnContaCorrente);
	    group.add(rdbtnContaPoupanca);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setFont(new Font("Arial", Font.PLAIN, 24));
		sl_panel.putConstraint(SpringLayout.SOUTH, btnSalvar, -10, SpringLayout.SOUTH, panel);
		sl_panel.putConstraint(SpringLayout.EAST, btnSalvar, 0, SpringLayout.EAST, nomeTxt);
		panel.add(btnSalvar);
		btnSalvar.setActionCommand("Salvar");
		
		JLabel lblRepetirSenha = new JLabel("Repetir senha");
		sl_panel.putConstraint(SpringLayout.WEST, lblRepetirSenha, 0, SpringLayout.WEST, lblNome);
		lblRepetirSenha.setFont(new Font("Arial", Font.PLAIN, 20));
		panel.add(lblRepetirSenha);
		
		repetirSenhaTxt = new JPasswordField();
		sl_panel.putConstraint(SpringLayout.NORTH, rdbtnContaCorrente, 51, SpringLayout.SOUTH, repetirSenhaTxt);
		sl_panel.putConstraint(SpringLayout.WEST, repetirSenhaTxt, 10, SpringLayout.EAST, lblRepetirSenha);
		sl_panel.putConstraint(SpringLayout.NORTH, lblRepetirSenha, 3, SpringLayout.NORTH, repetirSenhaTxt);
		sl_panel.putConstraint(SpringLayout.NORTH, repetirSenhaTxt, 30, SpringLayout.SOUTH, senhaTxt);
		sl_panel.putConstraint(SpringLayout.EAST, repetirSenhaTxt, -61, SpringLayout.EAST, panel);
		repetirSenhaTxt.setFont(new Font("Arial", Font.PLAIN, 20));
		repetirSenhaTxt.setColumns(10);
		panel.add(repetirSenhaTxt);
		btnSalvar.addActionListener(this);

		JPanel panelEsquerda = new JPanel();
		panelEsquerda.setPreferredSize(new Dimension(100, 100));
		this.add(panelEsquerda, BorderLayout.WEST);
		
		JPanel panelDireita = new JPanel();
		panelDireita.setPreferredSize(new Dimension(100, 100));
		this.add(panelDireita, BorderLayout.EAST);
		
		JPanel panelBottom = new JPanel();
		panelBottom.setPreferredSize(new Dimension(100, 100));
		this.add(panelBottom, BorderLayout.SOUTH);
		
		JPanel panelUp = new JPanel();
		panelUp.setPreferredSize(new Dimension(100, 20));
		this.add(panelUp, BorderLayout.NORTH);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if ("Salvar".equals(e.getActionCommand())) {
			if(verificaCampos(this.nomeTxt.getText(),
							  this.enderecoTxt.getText(),
							  this.cpfTxt.getText(),
							  this.usuarioTxt.getText(),
							  BancoUtil.converteSenha(this.senhaTxt.getPassword()),
							  BancoUtil.converteSenha(this.repetirSenhaTxt.getPassword()))) {
				Cliente novoCliente = new Cliente(this.nomeTxt.getText(),
												  this.cpfTxt.getText(),
												  this.enderecoTxt.getText(),
												  this.usuarioTxt.getText(),
												  BancoUtil.converteSenha(this.senhaTxt.getPassword()));
				ClienteDAO clienteDao = new ClienteDAO();
				clienteDao.adicionaCliente(novoCliente);
				Conta novaConta;
				if(this.rdbtnContaCorrente.isSelected()) {
					novaConta = new ContaCorrente(0);
				}else {
					novaConta = new ContaPoupanca(0);
				}
				novaConta.setTitular(novoCliente);
				ContaDAO contaDao = new ContaDAO();
				contaDao.adicionaConta(novaConta);
				JOptionPane.showMessageDialog(this, "Cliente n�mero "+novoCliente.getId()+" cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
				apagarCampos();
			} else {
				JOptionPane.showMessageDialog(this, "Erro no preenchimento do formul�rio!", "Erro", JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}


	protected boolean verificaCampos(String nome, String endereco, String cpf, String usuario, String senha1, String senha2) {
		boolean check = true;
		if(nome.equals("") ||
		   endereco.equals("") ||
		   !Cliente.validaCpf(cpf) ||
		   usuario.length()<=5 ||
		   senha1.length()<=5 || 
		   !senha2.equals(senha1)){
			check = false;
		}
		return check;
	}
	

	public void apagarCampos() {
		this.nomeTxt.setText("");
		this.enderecoTxt.setText("");
		this.cpfTxt.setText("");
		this.usuarioTxt.setText("");
		this.senhaTxt.setText("");
		this.repetirSenhaTxt.setText("");
		this.rdbtnContaCorrente.setSelected(true);
	}

	public JTextField getNomeTxt() {
		return nomeTxt;
	}

	public JTextField getEnderecoTxt() {
		return enderecoTxt;
	}

	public JTextField getCpfTxt() {
		return cpfTxt;
	}

	public JTextField getUsuarioTxt() {
		return usuarioTxt;
	}

	public JPasswordField getSenhaTxt() {
		return senhaTxt;
	}

	public JRadioButton getRdbtnContaCorrente() {
		return rdbtnContaCorrente;
	}

	public JRadioButton getRdbtnContaPoupanca() {
		return rdbtnContaPoupanca;
	}

	public JPasswordField getRepetirSenhaTxt() {
		return repetirSenhaTxt;
	}
	
	
	
}

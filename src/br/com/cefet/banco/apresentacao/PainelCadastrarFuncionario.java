package br.com.cefet.banco.apresentacao;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.TitledBorder;

import br.com.cefet.banco.negocio.Caixa;
import br.com.cefet.banco.negocio.Cliente;
import br.com.cefet.banco.negocio.Conta;
import br.com.cefet.banco.negocio.ContaCorrente;
import br.com.cefet.banco.negocio.ContaPoupanca;
import br.com.cefet.banco.negocio.Diretor;
import br.com.cefet.banco.negocio.Funcionario;
import br.com.cefet.banco.negocio.Gerente;
import br.com.cefet.banco.persistencia.bd.ClienteDAO;
import br.com.cefet.banco.persistencia.bd.ContaDAO;
import br.com.cefet.banco.persistencia.bd.FuncionarioDAO;
import br.com.cefet.banco.util.BancoUtil;

import javax.swing.JFormattedTextField;
import javax.swing.JComboBox;
import javax.swing.UIManager;
import java.awt.Color;

public class PainelCadastrarFuncionario extends JPanel implements ActionListener {
	private JTextField nomeTxt;
	private JTextField enderecoTxt;
	private JTextField cpfTxt;
	private JTextField usuarioTxt;
	private JPasswordField senhaTxt;
	private JPasswordField repetirSenhaTxt;
	private JTextField departamentoTxt;
	private JFormattedTextField salarioTxt;
	private JComboBox comboBox;
	
	private boolean primeiroFuncionario;

	/**
	 * Create the panel.
	 */
	public PainelCadastrarFuncionario(boolean p) {
		setMinimumSize(new Dimension(500, 400));
		setLayout(new BorderLayout(0, 0));

		this.primeiroFuncionario = p;
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Novo Funcion\u00E1rio", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
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
		sl_panel.putConstraint(SpringLayout.WEST, nomeTxt, 90, SpringLayout.EAST, lblNome);
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


		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setFont(new Font("Arial", Font.PLAIN, 24));
		sl_panel.putConstraint(SpringLayout.SOUTH, btnSalvar, -10, SpringLayout.SOUTH, panel);
		sl_panel.putConstraint(SpringLayout.EAST, btnSalvar, 0, SpringLayout.EAST, nomeTxt);
		panel.add(btnSalvar);
		btnSalvar.setActionCommand("Salvar");

		JLabel lblRepetirSenha = new JLabel("Repetir senha");
		sl_panel.putConstraint(SpringLayout.NORTH, lblRepetirSenha, 36, SpringLayout.SOUTH, lblSenha);
		sl_panel.putConstraint(SpringLayout.WEST, lblRepetirSenha, 0, SpringLayout.WEST, lblNome);
		lblRepetirSenha.setFont(new Font("Arial", Font.PLAIN, 20));
		panel.add(lblRepetirSenha);

		repetirSenhaTxt = new JPasswordField();
		sl_panel.putConstraint(SpringLayout.WEST, repetirSenhaTxt, 0, SpringLayout.WEST, nomeTxt);
		sl_panel.putConstraint(SpringLayout.NORTH, repetirSenhaTxt, 30, SpringLayout.SOUTH, senhaTxt);
		sl_panel.putConstraint(SpringLayout.EAST, repetirSenhaTxt, -61, SpringLayout.EAST, panel);
		repetirSenhaTxt.setFont(new Font("Arial", Font.PLAIN, 20));
		repetirSenhaTxt.setColumns(10);
		panel.add(repetirSenhaTxt);

		departamentoTxt = new JTextField();
		sl_panel.putConstraint(SpringLayout.NORTH, departamentoTxt, 30, SpringLayout.SOUTH, cpfTxt);
		sl_panel.putConstraint(SpringLayout.WEST, departamentoTxt, 0, SpringLayout.WEST, nomeTxt);
		sl_panel.putConstraint(SpringLayout.EAST, departamentoTxt, 0, SpringLayout.EAST, nomeTxt);
		departamentoTxt.setFont(new Font("Arial", Font.PLAIN, 20));
		panel.add(departamentoTxt);
		departamentoTxt.setColumns(10);

		JLabel lblDepartamento = new JLabel("Departamento");
		sl_panel.putConstraint(SpringLayout.NORTH, lblDepartamento, 36, SpringLayout.SOUTH, lblCpf);
		sl_panel.putConstraint(SpringLayout.WEST, lblDepartamento, 0, SpringLayout.WEST, lblNome);
		lblDepartamento.setFont(new Font("Arial", Font.PLAIN, 20));
		panel.add(lblDepartamento);

		NumberFormat paymentFormat = NumberFormat.getCurrencyInstance();

		salarioTxt = new JFormattedTextField(paymentFormat);
		sl_panel.putConstraint(SpringLayout.NORTH, usuarioTxt, 30, SpringLayout.SOUTH, salarioTxt);
		sl_panel.putConstraint(SpringLayout.NORTH, salarioTxt, 30, SpringLayout.SOUTH, departamentoTxt);
		sl_panel.putConstraint(SpringLayout.WEST, salarioTxt, 0, SpringLayout.WEST, nomeTxt);
		sl_panel.putConstraint(SpringLayout.EAST, salarioTxt, 0, SpringLayout.EAST, nomeTxt);
		salarioTxt.setFont(new Font("Arial", Font.PLAIN, 20));
		panel.add(salarioTxt);
		salarioTxt.setValue(0);

		JLabel lblSalrio = new JLabel("Sal\u00E1rio");
		sl_panel.putConstraint(SpringLayout.NORTH, lblSalrio, 36, SpringLayout.SOUTH, lblDepartamento);
		sl_panel.putConstraint(SpringLayout.WEST, lblSalrio, 0, SpringLayout.WEST, lblNome);
		lblSalrio.setFont(new Font("Arial", Font.PLAIN, 20));
		panel.add(lblSalrio);

		JLabel lblCargo = new JLabel("Cargo");
		sl_panel.putConstraint(SpringLayout.WEST, lblCargo, 0, SpringLayout.WEST, lblNome);
		lblCargo.setFont(new Font("Arial", Font.PLAIN, 20));
		panel.add(lblCargo);

		String[] cargoStrings = {"Caixa", "Gerente", "Diretor"};

		comboBox = new JComboBox(cargoStrings);
		sl_panel.putConstraint(SpringLayout.NORTH, lblCargo, 3, SpringLayout.NORTH, comboBox);
		sl_panel.putConstraint(SpringLayout.NORTH, comboBox, 30, SpringLayout.SOUTH, repetirSenhaTxt);
		sl_panel.putConstraint(SpringLayout.WEST, comboBox, 0, SpringLayout.WEST, nomeTxt);
		comboBox.setFont(new Font("Arial", Font.PLAIN, 20));
		comboBox.setSelectedIndex(0);
		panel.add(comboBox);
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

		if(primeiroFuncionario) {
			this.comboBox.setSelectedIndex(2);
			this.comboBox.setEnabled(false);
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if ("Salvar".equals(e.getActionCommand())) {
			if(verificaCampos(this.nomeTxt.getText(),
					  this.enderecoTxt.getText(),
					  this.cpfTxt.getText(),
					  this.departamentoTxt.getText(),
					  this.salarioTxt.getText(),
					  this.usuarioTxt.getText(),
					  BancoUtil.converteSenha(this.senhaTxt.getPassword()),
					  BancoUtil.converteSenha(this.repetirSenhaTxt.getPassword()))) {
				Funcionario novoFuncionario = null;
				NumberFormat paymentFormat = NumberFormat.getCurrencyInstance();
				try {
					switch (this.comboBox.getSelectedIndex()) {
						case 0:
							novoFuncionario = new Caixa(this.nomeTxt.getText(), 
														this.enderecoTxt.getText(), 
														this.cpfTxt.getText(), 
														this.departamentoTxt.getText(), 
														BancoUtil.converteSenha(this.senhaTxt.getPassword()), 
														this.usuarioTxt.getText(), 
														paymentFormat.parse(this.salarioTxt.getText()).doubleValue());
							break;
						case 1:
							novoFuncionario = new Gerente(this.nomeTxt.getText(), 
									this.enderecoTxt.getText(), 
									this.cpfTxt.getText(), 
									this.departamentoTxt.getText(), 
									BancoUtil.converteSenha(this.senhaTxt.getPassword()), 
									this.usuarioTxt.getText(), 
									paymentFormat.parse(this.salarioTxt.getText()).doubleValue());
							break;
						case 2:
							novoFuncionario = new Diretor(this.nomeTxt.getText(), 
									this.enderecoTxt.getText(), 
									this.cpfTxt.getText(), 
									this.departamentoTxt.getText(), 
									BancoUtil.converteSenha(this.senhaTxt.getPassword()), 
									this.usuarioTxt.getText(), 
									paymentFormat.parse(this.salarioTxt.getText()).doubleValue());
							break;
						default:
							break;
					}
					FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
					funcionarioDAO.adicionaFuncionario(novoFuncionario);
					JOptionPane.showMessageDialog(this, "Funcionário número "+novoFuncionario.getId()+" cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
					apagarCampos();
					if(primeiroFuncionario) {
						JOptionPane.showMessageDialog(this, "Reinicie o sistema para fazer o primeiro acesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
						System.exit(0);
					}
				} catch (ParseException e1) {
					JOptionPane.showMessageDialog(this, "Erro no preenchimento do formulário!", "Erro", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(this, "Erro no preenchimento do formulário!", "Erro", JOptionPane.ERROR_MESSAGE);
			}
		}

	}

	protected boolean verificaCampos(String nome, String endereco, String cpf, String departamento, String salario, String usuario, String senha1, String senha2) {
		boolean check = true;
		NumberFormat paymentFormat = NumberFormat.getCurrencyInstance();
		try {
			if(nome.equals("") ||
					endereco.equals("") ||
					!Cliente.validaCpf(cpf) ||
					departamento.equals("") ||
					paymentFormat.parse(salario).doubleValue() < 0 ||
					usuario.length()<5 ||
					senha1.length()<5 || 
					!senha2.equals(senha1)){
				check = false;
			}
		} catch (ParseException e) {
			check = false;
			e.printStackTrace();
		}
		return check;
	}
	

	public void apagarCampos() {
		this.nomeTxt.setText("");
		this.enderecoTxt.setText("");
		this.cpfTxt.setText("");
		this.departamentoTxt.setText("");
		this.salarioTxt.setValue(0);
		this.usuarioTxt.setText("");
		this.senhaTxt.setText("");
		this.repetirSenhaTxt.setText("");
		this.comboBox.setSelectedIndex(0);
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

	public JPasswordField getRepetirSenhaTxt() {
		return repetirSenhaTxt;
	}

	public JTextField getDepartamentoTxt() {
		return departamentoTxt;
	}

	public JFormattedTextField getSalarioTxt() {
		return salarioTxt;
	}

	public JComboBox getComboBox() {
		return comboBox;
	}

	public boolean isPrimeiroFuncionario() {
		return primeiroFuncionario;
	}
	
	

}

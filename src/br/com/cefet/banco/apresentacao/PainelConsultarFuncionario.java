package br.com.cefet.banco.apresentacao;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import br.com.cefet.banco.exceptions.DepositoInvalidoException;
import br.com.cefet.banco.exceptions.SaldoInsuficienteException;
import br.com.cefet.banco.negocio.Autenticavel;
import br.com.cefet.banco.negocio.Banco;
import br.com.cefet.banco.negocio.Caixa;
import br.com.cefet.banco.negocio.Cliente;
import br.com.cefet.banco.negocio.Conta;
import br.com.cefet.banco.negocio.ContaCorrente;
import br.com.cefet.banco.negocio.Diretor;
import br.com.cefet.banco.negocio.Funcionario;
import br.com.cefet.banco.negocio.Gerente;
import br.com.cefet.banco.persistencia.bd.ClienteDAO;
import br.com.cefet.banco.persistencia.bd.ContaDAO;
import br.com.cefet.banco.persistencia.bd.FuncionarioDAO;
import br.com.cefet.banco.util.BancoUtil;

public class PainelConsultarFuncionario extends JPanel implements ActionListener {
	private JTextField idFuncionarioTxt;
	private JTextField nomeTxt;
	private JTextField enderecoTxt;
	private JTextField cpfTxt;
	private JFormattedTextField salarioTxt;
	private JFormattedTextField bonificacaoTxt;
	private JPanel panelConta;
	private JButton btnSalvarAlteraes;
	private JComboBox comboBox;
	private Autenticavel usuarioLogado;

	Funcionario funcionarioEncontrado;
	FuncionarioDAO funcionarioDao;
	private JTextField departamentoTxt;

	/**
	 * Create the panel.
	 */
	public PainelConsultarFuncionario(Autenticavel a) {
		this.usuarioLogado = a;
		setLayout(new BorderLayout(0, 0));

		JPanel panelBuscaCliente = new JPanel();
		panelBuscaCliente.setBorder(null);
		add(panelBuscaCliente, BorderLayout.CENTER);
		SpringLayout sl_panelBuscaCliente = new SpringLayout();
		panelBuscaCliente.setLayout(sl_panelBuscaCliente);

		JLabel lblFuncionario = new JLabel("N\u00FAmero do funcion\u00E1rio");
		sl_panelBuscaCliente.putConstraint(SpringLayout.NORTH, lblFuncionario, 16, SpringLayout.NORTH, panelBuscaCliente);
		sl_panelBuscaCliente.putConstraint(SpringLayout.WEST, lblFuncionario, 10, SpringLayout.WEST, panelBuscaCliente);
		lblFuncionario.setFont(new Font("Arial", Font.PLAIN, 24));
		panelBuscaCliente.add(lblFuncionario);

		idFuncionarioTxt = new JTextField();
		idFuncionarioTxt.setMinimumSize(new Dimension(100, 22));
		sl_panelBuscaCliente.putConstraint(SpringLayout.WEST, idFuncionarioTxt, 6, SpringLayout.EAST, lblFuncionario);
		sl_panelBuscaCliente.putConstraint(SpringLayout.NORTH, idFuncionarioTxt, 16, SpringLayout.NORTH, panelBuscaCliente);
		idFuncionarioTxt.setFont(new Font("Arial", Font.PLAIN, 20));
		panelBuscaCliente.add(idFuncionarioTxt);
		idFuncionarioTxt.setColumns(10);

		JButton btnBuscar = new JButton("Buscar");
		sl_panelBuscaCliente.putConstraint(SpringLayout.EAST, btnBuscar, -10, SpringLayout.EAST, panelBuscaCliente);
		sl_panelBuscaCliente.putConstraint(SpringLayout.EAST, idFuncionarioTxt, -6, SpringLayout.WEST, btnBuscar);
		sl_panelBuscaCliente.putConstraint(SpringLayout.NORTH, btnBuscar, 16, SpringLayout.NORTH, panelBuscaCliente);
		sl_panelBuscaCliente.putConstraint(SpringLayout.SOUTH, btnBuscar, 45, SpringLayout.NORTH, panelBuscaCliente);
		btnBuscar.setFont(new Font("Arial", Font.PLAIN, 20));
		panelBuscaCliente.add(btnBuscar);
		btnBuscar.addActionListener(this);
		btnBuscar.setActionCommand("Buscar");

		JPanel panelDadosPessoais = new JPanel();
		sl_panelBuscaCliente.putConstraint(SpringLayout.NORTH, panelDadosPessoais, 6, SpringLayout.SOUTH, lblFuncionario);
		sl_panelBuscaCliente.putConstraint(SpringLayout.WEST, panelDadosPessoais, 10, SpringLayout.WEST, panelBuscaCliente);
		sl_panelBuscaCliente.putConstraint(SpringLayout.SOUTH, panelDadosPessoais, -10, SpringLayout.SOUTH, panelBuscaCliente);
		sl_panelBuscaCliente.putConstraint(SpringLayout.EAST, panelDadosPessoais, -10, SpringLayout.EAST, panelBuscaCliente);
		sl_panelBuscaCliente.putConstraint(SpringLayout.SOUTH, idFuncionarioTxt, -6, SpringLayout.NORTH, panelDadosPessoais);
		panelDadosPessoais.setBorder(new TitledBorder(null, "Dados pessoais", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelBuscaCliente.add(panelDadosPessoais);
		SpringLayout sl_panelDadosPessoais = new SpringLayout();
		panelDadosPessoais.setLayout(sl_panelDadosPessoais);

		JLabel lblNome = new JLabel("Nome");
		lblNome.setFont(new Font("Arial", Font.PLAIN, 18));
		sl_panelDadosPessoais.putConstraint(SpringLayout.NORTH, lblNome, 10, SpringLayout.NORTH, panelDadosPessoais);
		sl_panelDadosPessoais.putConstraint(SpringLayout.WEST, lblNome, 10, SpringLayout.WEST, panelDadosPessoais);
		panelDadosPessoais.add(lblNome);

		JLabel lblEndereo = new JLabel("Endere\u00E7o");
		sl_panelDadosPessoais.putConstraint(SpringLayout.NORTH, lblEndereo, 30, SpringLayout.SOUTH, lblNome);
		sl_panelDadosPessoais.putConstraint(SpringLayout.WEST, lblEndereo, 0, SpringLayout.WEST, lblNome);
		lblEndereo.setFont(new Font("Arial", Font.PLAIN, 18));
		panelDadosPessoais.add(lblEndereo);

		JLabel lblCpf = new JLabel("CPF");
		sl_panelDadosPessoais.putConstraint(SpringLayout.NORTH, lblCpf, 30, SpringLayout.SOUTH, lblEndereo);
		sl_panelDadosPessoais.putConstraint(SpringLayout.WEST, lblCpf, 0, SpringLayout.WEST, lblNome);
		lblCpf.setFont(new Font("Arial", Font.PLAIN, 18));
		panelDadosPessoais.add(lblCpf);

		nomeTxt = new JTextField();
		sl_panelDadosPessoais.putConstraint(SpringLayout.NORTH, nomeTxt, -3, SpringLayout.NORTH, lblNome);
		sl_panelDadosPessoais.putConstraint(SpringLayout.WEST, nomeTxt, 94, SpringLayout.EAST, lblNome);
		sl_panelDadosPessoais.putConstraint(SpringLayout.EAST, nomeTxt, -10, SpringLayout.EAST, panelDadosPessoais);
		nomeTxt.setFont(new Font("Arial", Font.PLAIN, 18));
		panelDadosPessoais.add(nomeTxt);
		nomeTxt.setColumns(10);

		enderecoTxt = new JTextField();
		sl_panelDadosPessoais.putConstraint(SpringLayout.NORTH, enderecoTxt, -3, SpringLayout.NORTH, lblEndereo);
		sl_panelDadosPessoais.putConstraint(SpringLayout.WEST, enderecoTxt, 0, SpringLayout.WEST, nomeTxt);
		sl_panelDadosPessoais.putConstraint(SpringLayout.EAST, enderecoTxt, 0, SpringLayout.EAST, nomeTxt);
		enderecoTxt.setFont(new Font("Arial", Font.PLAIN, 18));
		enderecoTxt.setColumns(10);
		panelDadosPessoais.add(enderecoTxt);

		cpfTxt = new JTextField();
		sl_panelDadosPessoais.putConstraint(SpringLayout.NORTH, cpfTxt, -3, SpringLayout.NORTH, lblCpf);
		sl_panelDadosPessoais.putConstraint(SpringLayout.WEST, cpfTxt, 0, SpringLayout.WEST, nomeTxt);
		sl_panelDadosPessoais.putConstraint(SpringLayout.EAST, cpfTxt, 0, SpringLayout.EAST, nomeTxt);
		cpfTxt.setFont(new Font("Arial", Font.PLAIN, 18));
		cpfTxt.setColumns(10);
		panelDadosPessoais.add(cpfTxt);

		panelConta = new JPanel();
		sl_panelDadosPessoais.putConstraint(SpringLayout.SOUTH, panelConta, -10, SpringLayout.SOUTH, panelDadosPessoais);
		panelConta.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Dados financeiros", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		sl_panelDadosPessoais.putConstraint(SpringLayout.WEST, panelConta, 10, SpringLayout.WEST, panelDadosPessoais);
		sl_panelDadosPessoais.putConstraint(SpringLayout.EAST, panelConta, -10, SpringLayout.EAST, panelDadosPessoais);
		panelDadosPessoais.add(panelConta);
		SpringLayout sl_panelConta = new SpringLayout();
		panelConta.setLayout(sl_panelConta);

		JLabel lblSalrio = new JLabel("Sal\u00E1rio");
		sl_panelConta.putConstraint(SpringLayout.NORTH, lblSalrio, 40, SpringLayout.NORTH, panelConta);
		sl_panelConta.putConstraint(SpringLayout.WEST, lblSalrio, 100, SpringLayout.WEST, panelConta);
		lblSalrio.setFont(new Font("Arial", Font.PLAIN, 20));
		panelConta.add(lblSalrio);

		NumberFormat paymentFormat = NumberFormat.getCurrencyInstance();

		salarioTxt = new JFormattedTextField(paymentFormat);
		sl_panelConta.putConstraint(SpringLayout.NORTH, salarioTxt, -7, SpringLayout.NORTH, lblSalrio);
		sl_panelConta.putConstraint(SpringLayout.WEST, salarioTxt, 31, SpringLayout.EAST, lblSalrio);
		sl_panelConta.putConstraint(SpringLayout.EAST, salarioTxt, -100, SpringLayout.EAST, panelConta);
		salarioTxt.setHorizontalAlignment(SwingConstants.RIGHT);
		salarioTxt.setFont(new Font("Arial", Font.PLAIN, 25));
		salarioTxt.setEditable(false);
		salarioTxt.setColumns(8);
		salarioTxt.setBackground(Color.WHITE);
		panelConta.add(salarioTxt);

		JLabel lblBonificao = new JLabel("Bonifica\u00E7\u00E3o");
		sl_panelConta.putConstraint(SpringLayout.NORTH, lblBonificao, 28, SpringLayout.SOUTH, lblSalrio);
		sl_panelConta.putConstraint(SpringLayout.EAST, lblBonificao, 0, SpringLayout.EAST, lblSalrio);
		lblBonificao.setFont(new Font("Arial", Font.PLAIN, 18));
		panelConta.add(lblBonificao);

		bonificacaoTxt = new JFormattedTextField(paymentFormat);
		sl_panelConta.putConstraint(SpringLayout.NORTH, bonificacaoTxt, -3, SpringLayout.NORTH, lblBonificao);
		sl_panelConta.putConstraint(SpringLayout.WEST, bonificacaoTxt, 0, SpringLayout.WEST, salarioTxt);
		sl_panelConta.putConstraint(SpringLayout.EAST, bonificacaoTxt, 0, SpringLayout.EAST, salarioTxt);
		bonificacaoTxt.setHorizontalAlignment(SwingConstants.RIGHT);
		bonificacaoTxt.setFont(new Font("Arial", Font.PLAIN, 18));
		bonificacaoTxt.setEditable(false);
		bonificacaoTxt.setBackground(Color.WHITE);
		panelConta.add(bonificacaoTxt);
		
		btnSalvarAlteraes = new JButton("Salvar altera\u00E7\u00F5es");
		sl_panelDadosPessoais.putConstraint(SpringLayout.NORTH, panelConta, 20, SpringLayout.SOUTH, btnSalvarAlteraes);
		sl_panelDadosPessoais.putConstraint(SpringLayout.EAST, btnSalvarAlteraes, 0, SpringLayout.EAST, nomeTxt);
		btnSalvarAlteraes.setActionCommand("Salvar alterações");
		btnSalvarAlteraes.addActionListener(this);
		btnSalvarAlteraes.setEnabled(false);
		btnSalvarAlteraes.setFont(new Font("Arial", Font.PLAIN, 18));
		panelDadosPessoais.add(btnSalvarAlteraes);
		
		JLabel lblDepartamento = new JLabel("Departamento");
		sl_panelDadosPessoais.putConstraint(SpringLayout.NORTH, lblDepartamento, 30, SpringLayout.SOUTH, lblCpf);
		sl_panelDadosPessoais.putConstraint(SpringLayout.WEST, lblDepartamento, 0, SpringLayout.WEST, lblNome);
		lblDepartamento.setFont(new Font("Arial", Font.PLAIN, 18));
		panelDadosPessoais.add(lblDepartamento);
		
		departamentoTxt = new JTextField();
		sl_panelDadosPessoais.putConstraint(SpringLayout.NORTH, departamentoTxt, -3, SpringLayout.NORTH, lblDepartamento);
		sl_panelDadosPessoais.putConstraint(SpringLayout.WEST, departamentoTxt, 0, SpringLayout.WEST, nomeTxt);
		sl_panelDadosPessoais.putConstraint(SpringLayout.EAST, departamentoTxt, 0, SpringLayout.EAST, nomeTxt);
		departamentoTxt.setFont(new Font("Arial", Font.PLAIN, 18));
		departamentoTxt.setColumns(10);
		panelDadosPessoais.add(departamentoTxt);
		
		JLabel lblCargo = new JLabel("Cargo");
		sl_panelDadosPessoais.putConstraint(SpringLayout.NORTH, btnSalvarAlteraes, -4, SpringLayout.NORTH, lblCargo);
		sl_panelDadosPessoais.putConstraint(SpringLayout.NORTH, lblCargo, 30, SpringLayout.SOUTH, lblDepartamento);
		sl_panelDadosPessoais.putConstraint(SpringLayout.WEST, lblCargo, 0, SpringLayout.WEST, lblNome);
		lblCargo.setFont(new Font("Arial", Font.PLAIN, 18));
		panelDadosPessoais.add(lblCargo);
		
		String[] cargoStrings = {"Caixa", "Gerente", "Diretor"};
		comboBox = new JComboBox(cargoStrings);
		sl_panelDadosPessoais.putConstraint(SpringLayout.NORTH, comboBox, -3, SpringLayout.NORTH, lblCargo);
		sl_panelDadosPessoais.putConstraint(SpringLayout.WEST, comboBox, 0, SpringLayout.WEST, nomeTxt);
		comboBox.setSelectedIndex(0);
		comboBox.setEnabled(false);
		comboBox.setFont(new Font("Arial", Font.PLAIN, 18));
		panelDadosPessoais.add(comboBox);

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

	protected Funcionario buscarFuncionario(long idFuncionario, Autenticavel usuarioLogado) {
		Funcionario funcionarioEncontrado = funcionarioDao.getFuncionario(idFuncionario);
		if(!(usuarioLogado instanceof Diretor) && !(funcionarioEncontrado instanceof Caixa)) {
			return null;
		} else {
			return funcionarioEncontrado;
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if ("Buscar".equals(e.getActionCommand())) {
			if(this.idFuncionarioTxt.getText().compareTo("")!=0) {
				// Buscar funcionario por id
				funcionarioDao = new FuncionarioDAO();
				this.funcionarioEncontrado = buscarFuncionario(Long.valueOf(this.idFuncionarioTxt.getText()), this.usuarioLogado);
				if (funcionarioEncontrado != null) {
					this.nomeTxt.setText(funcionarioEncontrado.getNome());
					this.enderecoTxt.setText(funcionarioEncontrado.getEndereco());
					this.cpfTxt.setText(funcionarioEncontrado.getCpf());				
					this.salarioTxt.setValue(funcionarioEncontrado.getSalario());
					this.bonificacaoTxt.setValue(funcionarioEncontrado.getBonificacao());
					this.departamentoTxt.setText(funcionarioEncontrado.getDepartamento());
					this.comboBox.setSelectedIndex(funcionarioEncontrado.getCargo());
					this.btnSalvarAlteraes.setEnabled(true);
				} else {
					this.btnSalvarAlteraes.setEnabled(false);
					JOptionPane.showMessageDialog(this, "Funcionário não encontrado!", "Erro", JOptionPane.WARNING_MESSAGE);
					apagaCampos();
				}
			} else {
				JOptionPane.showMessageDialog(this, "Digite o número do funcionário!", "Erro", JOptionPane.WARNING_MESSAGE);
			}
		} else {
			if("Salvar alterações".equals(e.getActionCommand())) {
				if(verificaCampos(this.nomeTxt.getText(),
						  this.enderecoTxt.getText(),
						  this.cpfTxt.getText(),
						  this.departamentoTxt.getText(),
						  this.salarioTxt.getText())) {
					Funcionario funcionarioAlterado = null;
					NumberFormat paymentFormat = NumberFormat.getCurrencyInstance();
					try {
						switch (this.comboBox.getSelectedIndex()) {
							case 0:
								funcionarioAlterado = new Caixa(this.nomeTxt.getText(), 
															this.enderecoTxt.getText(), 
															this.cpfTxt.getText(), 
															this.departamentoTxt.getText(), 
															funcionarioEncontrado.getSenha(), 
															funcionarioEncontrado.getUsuario(), 
															paymentFormat.parse(this.salarioTxt.getText()).doubleValue());
								break;
							case 1:
								funcionarioAlterado = new Gerente(this.nomeTxt.getText(), 
										this.enderecoTxt.getText(), 
										this.cpfTxt.getText(), 
										this.departamentoTxt.getText(), 
										funcionarioEncontrado.getSenha(), 
										funcionarioEncontrado.getUsuario(), 
										paymentFormat.parse(this.salarioTxt.getText()).doubleValue());
								break;
							case 2:
								funcionarioAlterado = new Diretor(this.nomeTxt.getText(), 
										this.enderecoTxt.getText(), 
										this.cpfTxt.getText(), 
										this.departamentoTxt.getText(), 
										funcionarioEncontrado.getSenha(), 
										funcionarioEncontrado.getUsuario(), 
										paymentFormat.parse(this.salarioTxt.getText()).doubleValue());
								break;
							default:
								break;
						}
						funcionarioAlterado.setId(funcionarioEncontrado.getId());
						FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
						funcionarioDAO.altera(funcionarioAlterado);
						JOptionPane.showMessageDialog(this, "Funcionário número "+funcionarioAlterado.getId()+" alterado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
					} catch (ParseException e1) {
						JOptionPane.showMessageDialog(this, "Erro no preenchimento do formulário!", "Erro", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					}
				}
			} else {
				JOptionPane.showMessageDialog(this, "Erro no preenchimento do formulário!", "Erro", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	public void apagaCampos() {
		this.funcionarioEncontrado = null;
		this.nomeTxt.setText("");
		this.enderecoTxt.setText("");
		this.cpfTxt.setText("");
		this.departamentoTxt.setText("");
		this.salarioTxt.setText("");
		this.bonificacaoTxt.setText("");
		this.comboBox.setSelectedIndex(0);
		this.btnSalvarAlteraes.setEnabled(false);
		this.comboBox.setEnabled(false);
	}
	
	protected boolean verificaCampos(String nome, String endereco, String cpf, String departamento, String salario) {
		boolean check = true;
		NumberFormat paymentFormat = NumberFormat.getCurrencyInstance();
		try {
			if(nome.equals("") ||
					endereco.equals("") ||
					!Cliente.validaCpf(cpf) ||
					departamento.equals("") ||
					paymentFormat.parse(salario).doubleValue() <= 0){
				check = false;
			}
		} catch (ParseException e) {
			check = false;
			e.printStackTrace();
		}
		return check;
	}

	public JTextField getIdFuncionarioTxt() {
		return idFuncionarioTxt;
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

	public JFormattedTextField getSalarioTxt() {
		return salarioTxt;
	}

	public JFormattedTextField getBonificacaoTxt() {
		return bonificacaoTxt;
	}

	public JPanel getPanelConta() {
		return panelConta;
	}

	public JButton getBtnSalvarAlteraes() {
		return btnSalvarAlteraes;
	}

	public JComboBox getComboBox() {
		return comboBox;
	}

	public Funcionario getFuncionarioEncontrado() {
		return funcionarioEncontrado;
	}

	public JTextField getDepartamentoTxt() {
		return departamentoTxt;
	}
	
	
}

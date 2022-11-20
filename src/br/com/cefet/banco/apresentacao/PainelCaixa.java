package br.com.cefet.banco.apresentacao;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;

import br.com.cefet.banco.exceptions.DepositoInvalidoException;
import br.com.cefet.banco.exceptions.SaldoInsuficienteException;
import br.com.cefet.banco.negocio.Autenticavel;
import br.com.cefet.banco.negocio.Caixa;
import br.com.cefet.banco.negocio.Cliente;
import br.com.cefet.banco.negocio.Conta;
import br.com.cefet.banco.negocio.ContaCorrente;
import br.com.cefet.banco.persistencia.bd.ClienteDAO;
import br.com.cefet.banco.persistencia.bd.ContaDAO;
import br.com.cefet.banco.persistencia.bd.FuncionarioDAO;

import java.awt.Dimension;
import javax.swing.JFormattedTextField;
import java.text.Format;
import java.text.NumberFormat;
import java.awt.Color;
import javax.swing.SwingConstants;

public class PainelCaixa extends JPanel implements ActionListener {
	private JTextField idClienteTxt;
	private JTextField nomeTxt;
	private JTextField enderecoTxt;
	private JTextField cpfTxt;
	private JFormattedTextField saldoTxt;
	private JFormattedTextField limiteTxt;
	private JButton depositarBtn;
	private JButton sacarBtn;
	private JPanel panelConta;

	Cliente clienteEncontrado;
	Conta conta;
	ContaDAO contaDao;
	ClienteDAO clienteDao;

	/**
	 * Create the panel.
	 */
	public PainelCaixa() {
		setLayout(new BorderLayout(0, 0));

		JPanel panelBuscaCliente = new JPanel();
		panelBuscaCliente.setBorder(null);
		add(panelBuscaCliente, BorderLayout.CENTER);
		SpringLayout sl_panelBuscaCliente = new SpringLayout();
		panelBuscaCliente.setLayout(sl_panelBuscaCliente);

		JLabel lblCliente = new JLabel("N\u00FAmero do cliente");
		sl_panelBuscaCliente.putConstraint(SpringLayout.NORTH, lblCliente, 16, SpringLayout.NORTH, panelBuscaCliente);
		sl_panelBuscaCliente.putConstraint(SpringLayout.WEST, lblCliente, 10, SpringLayout.WEST, panelBuscaCliente);
		lblCliente.setFont(new Font("Arial", Font.PLAIN, 24));
		panelBuscaCliente.add(lblCliente);

		idClienteTxt = new JTextField();
		idClienteTxt.setMinimumSize(new Dimension(100, 22));
		sl_panelBuscaCliente.putConstraint(SpringLayout.WEST, idClienteTxt, 6, SpringLayout.EAST, lblCliente);
		sl_panelBuscaCliente.putConstraint(SpringLayout.NORTH, idClienteTxt, 16, SpringLayout.NORTH, panelBuscaCliente);
		idClienteTxt.setFont(new Font("Arial", Font.PLAIN, 20));
		panelBuscaCliente.add(idClienteTxt);
		idClienteTxt.setColumns(10);

		JButton btnBuscar = new JButton("Buscar");
		sl_panelBuscaCliente.putConstraint(SpringLayout.EAST, btnBuscar, -10, SpringLayout.EAST, panelBuscaCliente);
		sl_panelBuscaCliente.putConstraint(SpringLayout.EAST, idClienteTxt, -6, SpringLayout.WEST, btnBuscar);
		sl_panelBuscaCliente.putConstraint(SpringLayout.NORTH, btnBuscar, 16, SpringLayout.NORTH, panelBuscaCliente);
		sl_panelBuscaCliente.putConstraint(SpringLayout.SOUTH, btnBuscar, 45, SpringLayout.NORTH, panelBuscaCliente);
		btnBuscar.setFont(new Font("Arial", Font.PLAIN, 20));
		panelBuscaCliente.add(btnBuscar);
		btnBuscar.addActionListener(this);
		btnBuscar.setActionCommand("Buscar");

		JPanel panelDadosPessoais = new JPanel();
		sl_panelBuscaCliente.putConstraint(SpringLayout.NORTH, panelDadosPessoais, 6, SpringLayout.SOUTH, lblCliente);
		sl_panelBuscaCliente.putConstraint(SpringLayout.WEST, panelDadosPessoais, 10, SpringLayout.WEST, panelBuscaCliente);
		sl_panelBuscaCliente.putConstraint(SpringLayout.SOUTH, panelDadosPessoais, -10, SpringLayout.SOUTH, panelBuscaCliente);
		sl_panelBuscaCliente.putConstraint(SpringLayout.EAST, panelDadosPessoais, -10, SpringLayout.EAST, panelBuscaCliente);
		sl_panelBuscaCliente.putConstraint(SpringLayout.SOUTH, idClienteTxt, -6, SpringLayout.NORTH, panelDadosPessoais);
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
		nomeTxt.setEditable(false);
		sl_panelDadosPessoais.putConstraint(SpringLayout.NORTH, nomeTxt, -3, SpringLayout.NORTH, lblNome);
		sl_panelDadosPessoais.putConstraint(SpringLayout.WEST, nomeTxt, 94, SpringLayout.EAST, lblNome);
		sl_panelDadosPessoais.putConstraint(SpringLayout.EAST, nomeTxt, -10, SpringLayout.EAST, panelDadosPessoais);
		nomeTxt.setFont(new Font("Arial", Font.PLAIN, 18));
		panelDadosPessoais.add(nomeTxt);
		nomeTxt.setColumns(10);

		enderecoTxt = new JTextField();
		enderecoTxt.setEditable(false);
		sl_panelDadosPessoais.putConstraint(SpringLayout.NORTH, enderecoTxt, -3, SpringLayout.NORTH, lblEndereo);
		sl_panelDadosPessoais.putConstraint(SpringLayout.WEST, enderecoTxt, 0, SpringLayout.WEST, nomeTxt);
		sl_panelDadosPessoais.putConstraint(SpringLayout.EAST, enderecoTxt, 0, SpringLayout.EAST, nomeTxt);
		enderecoTxt.setFont(new Font("Arial", Font.PLAIN, 18));
		enderecoTxt.setColumns(10);
		panelDadosPessoais.add(enderecoTxt);

		cpfTxt = new JTextField();
		cpfTxt.setEditable(false);
		sl_panelDadosPessoais.putConstraint(SpringLayout.NORTH, cpfTxt, -3, SpringLayout.NORTH, lblCpf);
		sl_panelDadosPessoais.putConstraint(SpringLayout.WEST, cpfTxt, 0, SpringLayout.WEST, nomeTxt);
		sl_panelDadosPessoais.putConstraint(SpringLayout.EAST, cpfTxt, 0, SpringLayout.EAST, nomeTxt);
		cpfTxt.setFont(new Font("Arial", Font.PLAIN, 18));
		cpfTxt.setColumns(10);
		panelDadosPessoais.add(cpfTxt);

		panelConta = new JPanel();
		panelConta.setBorder(new TitledBorder(null, "Conta", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		sl_panelDadosPessoais.putConstraint(SpringLayout.NORTH, panelConta, 43, SpringLayout.SOUTH, cpfTxt);
		sl_panelDadosPessoais.putConstraint(SpringLayout.WEST, panelConta, 10, SpringLayout.WEST, panelDadosPessoais);
		sl_panelDadosPessoais.putConstraint(SpringLayout.SOUTH, panelConta, -10, SpringLayout.SOUTH, panelDadosPessoais);
		sl_panelDadosPessoais.putConstraint(SpringLayout.EAST, panelConta, -10, SpringLayout.EAST, panelDadosPessoais);
		panelDadosPessoais.add(panelConta);
		SpringLayout sl_panelConta = new SpringLayout();
		panelConta.setLayout(sl_panelConta);

		JLabel label = new JLabel("Saldo");
		sl_panelConta.putConstraint(SpringLayout.NORTH, label, 50, SpringLayout.NORTH, panelConta);
		sl_panelConta.putConstraint(SpringLayout.WEST, label, 100, SpringLayout.WEST, panelConta);
		label.setFont(new Font("Arial", Font.PLAIN, 20));
		panelConta.add(label);

		NumberFormat paymentFormat = NumberFormat.getCurrencyInstance();

		saldoTxt = new JFormattedTextField(paymentFormat);
		sl_panelConta.putConstraint(SpringLayout.NORTH, saldoTxt, -7, SpringLayout.NORTH, label);
		sl_panelConta.putConstraint(SpringLayout.WEST, saldoTxt, 31, SpringLayout.EAST, label);
		sl_panelConta.putConstraint(SpringLayout.EAST, saldoTxt, -100, SpringLayout.EAST, panelConta);
		saldoTxt.setHorizontalAlignment(SwingConstants.RIGHT);
		saldoTxt.setFont(new Font("Arial", Font.PLAIN, 25));
		saldoTxt.setEditable(false);
		saldoTxt.setColumns(8);
		saldoTxt.setBackground(Color.WHITE);
		panelConta.add(saldoTxt);

		JLabel label_1 = new JLabel("Limite");
		sl_panelConta.putConstraint(SpringLayout.NORTH, label_1, 28, SpringLayout.SOUTH, label);
		sl_panelConta.putConstraint(SpringLayout.WEST, label_1, 0, SpringLayout.WEST, label);
		label_1.setFont(new Font("Arial", Font.PLAIN, 18));
		panelConta.add(label_1);

		limiteTxt = new JFormattedTextField(paymentFormat);
		sl_panelConta.putConstraint(SpringLayout.NORTH, limiteTxt, -3, SpringLayout.NORTH, label_1);
		sl_panelConta.putConstraint(SpringLayout.WEST, limiteTxt, 0, SpringLayout.WEST, saldoTxt);
		sl_panelConta.putConstraint(SpringLayout.EAST, limiteTxt, 0, SpringLayout.EAST, saldoTxt);
		limiteTxt.setHorizontalAlignment(SwingConstants.RIGHT);
		limiteTxt.setFont(new Font("Arial", Font.PLAIN, 18));
		limiteTxt.setEditable(false);
		limiteTxt.setBackground(Color.WHITE);
		panelConta.add(limiteTxt);

		depositarBtn = new JButton("Depositar");
		depositarBtn.setEnabled(false);
		sl_panelConta.putConstraint(SpringLayout.WEST, depositarBtn, 10, SpringLayout.WEST, panelConta);
		depositarBtn.setPreferredSize(new Dimension(100, 25));
		depositarBtn.setFont(new Font("Arial", Font.PLAIN, 25));
		depositarBtn.setActionCommand("Depositar");
		depositarBtn.addActionListener(this);
		panelConta.add(depositarBtn);

		sacarBtn = new JButton("Sacar");
		sacarBtn.setEnabled(false);
		sl_panelConta.putConstraint(SpringLayout.NORTH, depositarBtn, 23, SpringLayout.SOUTH, sacarBtn);
		sl_panelConta.putConstraint(SpringLayout.SOUTH, depositarBtn, 60, SpringLayout.SOUTH, sacarBtn);
		sl_panelConta.putConstraint(SpringLayout.EAST, depositarBtn, 0, SpringLayout.EAST, sacarBtn);
		sl_panelConta.putConstraint(SpringLayout.EAST, sacarBtn, 84, SpringLayout.EAST, saldoTxt);
		sl_panelConta.putConstraint(SpringLayout.NORTH, sacarBtn, 32, SpringLayout.SOUTH, limiteTxt);
		sl_panelConta.putConstraint(SpringLayout.WEST, sacarBtn, 10, SpringLayout.WEST, panelConta);
		sacarBtn.setFont(new Font("Arial", Font.PLAIN, 25));
		sacarBtn.setActionCommand("Sacar");
		sacarBtn.addActionListener(this);
		panelConta.add(sacarBtn);

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
		if ("Buscar".equals(e.getActionCommand())) {
			if(this.idClienteTxt.getText().compareTo("")!=0) {
				// Buscar cliente por id
				clienteDao = new ClienteDAO();
				this.clienteEncontrado = clienteDao.getCliente(Long.valueOf(this.idClienteTxt.getText()));

				if(clienteEncontrado != null) {
					contaDao = new ContaDAO();
					this.conta = contaDao.getContaDeCliente(clienteEncontrado);

					this.nomeTxt.setText(clienteEncontrado.getNome());
					this.enderecoTxt.setText(clienteEncontrado.getEndereco());
					this.cpfTxt.setText(clienteEncontrado.getCpf());
					this.saldoTxt.setText("");
					this.limiteTxt.setText("");
					
					if (conta != null) {
						
						this.saldoTxt.setValue(conta.getSaldo());
						this.limiteTxt.setValue(conta.getLimite());
						this.sacarBtn.setEnabled(true);
						this.depositarBtn.setEnabled(true);
						if (conta instanceof ContaCorrente) {
							this.panelConta.setBorder(BorderFactory.createTitledBorder("Conta Corrente"));
						} else {
							this.panelConta.setBorder(BorderFactory.createTitledBorder("Conta Poupança"));
						}
					} else {
						this.sacarBtn.setEnabled(false);
						this.depositarBtn.setEnabled(false);
					}

				} else {
					JOptionPane.showMessageDialog(this, "Cliente não encontrado!", "Erro", JOptionPane.WARNING_MESSAGE);
				}

			} else {
				JOptionPane.showMessageDialog(this, "Digite o número do cliente!", "Erro", JOptionPane.WARNING_MESSAGE);
			}


		} else {
			double valor = Double.valueOf(JOptionPane.showInputDialog(this, "Insira o valor"));
			try {
				if ("Sacar".equals(e.getActionCommand())) {
					conta.sacar(valor);
				} else if ("Depositar".equals(e.getActionCommand())){
					conta.depositar(valor);
				}
				contaDao.altera(conta);
				saldoTxt.setValue(conta.getSaldo());
			} catch (SaldoInsuficienteException e1) {
				JOptionPane.showMessageDialog(this, "Saldo insuficiente!", "Erro", JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
			} catch (DepositoInvalidoException e2) {
				JOptionPane.showMessageDialog(this, "Depósito inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
				e2.printStackTrace();
			} catch (Exception e3) {
				e3.printStackTrace();
			}
		}
	}

	public JTextField getIdClienteTxt() {
		return idClienteTxt;
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

	public JFormattedTextField getSaldoTxt() {
		return saldoTxt;
	}

	public JFormattedTextField getLimiteTxt() {
		return limiteTxt;
	}

	public JButton getDepositarBtn() {
		return depositarBtn;
	}

	public JButton getSacarBtn() {
		return sacarBtn;
	}

	public JPanel getPanelConta() {
		return panelConta;
	}

	public Cliente getClienteEncontrado() {
		return clienteEncontrado;
	}

	public Conta getConta() {
		return conta;
	}
	
	
	
}

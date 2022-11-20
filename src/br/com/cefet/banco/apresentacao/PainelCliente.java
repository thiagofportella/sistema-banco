package br.com.cefet.banco.apresentacao;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.border.TitledBorder;

import br.com.cefet.banco.exceptions.DepositoInvalidoException;
import br.com.cefet.banco.exceptions.SaldoInsuficienteException;
import br.com.cefet.banco.negocio.Autenticavel;
import br.com.cefet.banco.negocio.Cliente;
import br.com.cefet.banco.negocio.Conta;
import br.com.cefet.banco.persistencia.bd.ClienteDAO;
import br.com.cefet.banco.persistencia.bd.ContaDAO;
import br.com.cefet.banco.persistencia.bd.FuncionarioDAO;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Insets;
import java.text.NumberFormat;

import javax.swing.JFormattedTextField;
import java.awt.Cursor;
import javax.swing.SwingConstants;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.SpringLayout;
import javax.swing.JButton;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JTextField;

public class PainelCliente extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8978318293080494720L;


	JFormattedTextField valorLimite;
	JFormattedTextField valorSaldo;
	Cliente clienteLogado;
	Conta conta;
	ContaDAO contaDao;

	double valor;
	private JTextField textField;

	/**
	 * Create the panel.
	 */
	public PainelCliente(Cliente c) {
		this.clienteLogado = c;
		contaDao = new ContaDAO();
		conta = contaDao.getContaDeCliente(clienteLogado);
		setLayout(new BorderLayout(0, 0));

		JPanel bordasClientePnl = new JPanel();
		bordasClientePnl.setBorder(new TitledBorder(null, "Cliente", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(bordasClientePnl, BorderLayout.CENTER);
		bordasClientePnl.setLayout(new BorderLayout(0, 0));

		NumberFormat paymentFormat = NumberFormat.getCurrencyInstance();

		JPanel panel = new JPanel();
		bordasClientePnl.add(panel, BorderLayout.CENTER);
		SpringLayout sl_panel = new SpringLayout();
		panel.setLayout(sl_panel);

		JLabel lblNewLabel = new JLabel("Saldo");
		sl_panel.putConstraint(SpringLayout.NORTH, lblNewLabel, 100, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.WEST, lblNewLabel, 156, SpringLayout.WEST, panel);
		sl_panel.putConstraint(SpringLayout.EAST, lblNewLabel, 238, SpringLayout.WEST, panel);
		panel.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		valorSaldo = new JFormattedTextField(paymentFormat);
		sl_panel.putConstraint(SpringLayout.NORTH, valorSaldo, 27, SpringLayout.NORTH, lblNewLabel);
		sl_panel.putConstraint(SpringLayout.WEST, valorSaldo, 106, SpringLayout.EAST, lblNewLabel);
		sl_panel.putConstraint(SpringLayout.EAST, valorSaldo, -156, SpringLayout.EAST, panel);
		panel.add(valorSaldo);
		valorSaldo.setBackground(SystemColor.text);
		valorSaldo.setHorizontalAlignment(SwingConstants.RIGHT);
		valorSaldo.setColumns(8);
		valorSaldo.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		valorSaldo.setFont(new Font("Arial", Font.PLAIN, 25));
		valorSaldo.setEditable(false);
		valorSaldo.setValue(conta.getSaldo());

		JLabel lblLimite = new JLabel("Limite");
		sl_panel.putConstraint(SpringLayout.SOUTH, lblNewLabel, -29, SpringLayout.NORTH, lblLimite);
		sl_panel.putConstraint(SpringLayout.SOUTH, lblLimite, 315, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.NORTH, lblLimite, 222, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.EAST, lblLimite, 238, SpringLayout.WEST, panel);
		sl_panel.putConstraint(SpringLayout.WEST, lblLimite, 156, SpringLayout.WEST, panel);
		panel.add(lblLimite);
		lblLimite.setFont(new Font("Arial", Font.PLAIN, 18));

		valorLimite = new JFormattedTextField(paymentFormat);
		valorLimite.setBackground(SystemColor.text);
		valorLimite.setEditable(false);
		valorLimite.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		sl_panel.putConstraint(SpringLayout.NORTH, valorLimite, 32, SpringLayout.NORTH, lblLimite);
		sl_panel.putConstraint(SpringLayout.WEST, valorLimite, 106, SpringLayout.EAST, lblLimite);
		sl_panel.putConstraint(SpringLayout.EAST, valorLimite, 0, SpringLayout.EAST, valorSaldo);
		panel.add(valorLimite);
		valorLimite.setHorizontalAlignment(SwingConstants.RIGHT);
		valorLimite.setFont(new Font("Arial", Font.PLAIN, 18));
		valorLimite.setValue(conta.getLimite());

		JButton btnDepositar = new JButton("Depositar");
		sl_panel.putConstraint(SpringLayout.NORTH, btnDepositar, -302, SpringLayout.SOUTH, panel);
		sl_panel.putConstraint(SpringLayout.WEST, btnDepositar, 156, SpringLayout.WEST, panel);
		sl_panel.putConstraint(SpringLayout.SOUTH, btnDepositar, -247, SpringLayout.SOUTH, panel);
		sl_panel.putConstraint(SpringLayout.EAST, btnDepositar, 0, SpringLayout.EAST, valorSaldo);
		btnDepositar.setPreferredSize(new Dimension(100, 25));
		btnDepositar.setFont(new Font("Arial", Font.PLAIN, 25));
		panel.add(btnDepositar);
		btnDepositar.addActionListener(this);
		btnDepositar.setActionCommand("Depositar");

		JButton btnSacar = new JButton("Sacar");
		sl_panel.putConstraint(SpringLayout.NORTH, btnSacar, 21, SpringLayout.SOUTH, btnDepositar);
		sl_panel.putConstraint(SpringLayout.WEST, btnSacar, 0, SpringLayout.WEST, lblNewLabel);
		sl_panel.putConstraint(SpringLayout.SOUTH, btnSacar, -171, SpringLayout.SOUTH, panel);
		sl_panel.putConstraint(SpringLayout.EAST, btnSacar, -156, SpringLayout.EAST, panel);
		btnSacar.setFont(new Font("Arial", Font.PLAIN, 25));
		panel.add(btnSacar);
		btnSacar.addActionListener(this);
		btnSacar.setActionCommand("Sacar");
		
		JLabel lblNmeroDoCliente = new JLabel("N\u00FAmero do cliente:");
		lblNmeroDoCliente.setFont(new Font("Arial", Font.PLAIN, 13));
		sl_panel.putConstraint(SpringLayout.WEST, lblNmeroDoCliente, 0, SpringLayout.WEST, lblNewLabel);
		sl_panel.putConstraint(SpringLayout.SOUTH, lblNmeroDoCliente, -30, SpringLayout.NORTH, lblNewLabel);
		panel.add(lblNmeroDoCliente);
		
		textField = new JTextField();
		textField.setEditable(false);
		sl_panel.putConstraint(SpringLayout.NORTH, textField, -3, SpringLayout.NORTH, lblNmeroDoCliente);
		sl_panel.putConstraint(SpringLayout.WEST, textField, 14, SpringLayout.EAST, lblNmeroDoCliente);
		textField.setText("1");
		panel.add(textField);
		textField.setColumns(10);

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
		// criar janela pedindo valor
		valor = Double.valueOf(JOptionPane.showInputDialog(this, "Insira o valor"));
		try {
			if ("Sacar".equals(e.getActionCommand())) {
				conta.sacar(valor);
			} else if ("Depositar".equals(e.getActionCommand())){
				conta.depositar(valor);
			}
			contaDao.altera(conta);
			valorSaldo.setValue(conta.getSaldo());
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

	public JFormattedTextField getValorLimite() {
		return valorLimite;
	}

	public JFormattedTextField getValorSaldo() {
		return valorSaldo;
	}

	public Cliente getClienteLogado() {
		return clienteLogado;
	}

	public Conta getConta() {
		return conta;
	}

	public ContaDAO getContaDao() {
		return contaDao;
	}

	public double getValor() {
		return valor;
	}

	public JTextField getTextField() {
		return textField;
	}
	
	
	
}

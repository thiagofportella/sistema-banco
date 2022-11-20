package br.com.cefet.banco.apresentacao;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import br.com.cefet.banco.apresentacao.panels.ClientesTableModel;
import br.com.cefet.banco.apresentacao.panels.FuncionariosTableModel;
import br.com.cefet.banco.negocio.Banco;
import br.com.cefet.banco.negocio.Caixa;
import br.com.cefet.banco.negocio.Cliente;
import br.com.cefet.banco.negocio.Conta;
import br.com.cefet.banco.negocio.Diretor;
import br.com.cefet.banco.negocio.Funcionario;
import br.com.cefet.banco.negocio.Gerente;
import br.com.cefet.banco.persistencia.bd.ClienteDAO;
import br.com.cefet.banco.persistencia.bd.ContaDAO;
import br.com.cefet.banco.persistencia.bd.FuncionarioDAO;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

public class PainelRelatorioGeral extends JPanel  implements ActionListener {
	
	private Banco banco;
	
	private JPanel panelPagamentos;
	private JFormattedTextField txtTotalLiquido;
	private JTable tableClientes;
	private JTable tableFuncionarios;
	private List<Cliente> listaClientes;
	private List<Funcionario> listaFuncionarios;
	private JFormattedTextField txtTotalReceita;
	private JFormattedTextField txtTotalPagamentos;
	private JFormattedTextField txtTaxa;
	

	/**
	 * Create the panel.
	 */
	public PainelRelatorioGeral() {
		setLayout(new BorderLayout(0, 0));
		
		initialize();
	}
	
    private void initColumnSizesClientes(JTable table) {
    	ClientesTableModel model = (ClientesTableModel) table.getModel();
        TableColumn column = null;
        Component comp = null;
        int headerWidth = 0;
        int cellWidth = 0;
        Object[] longValues = model.longValues;
        TableCellRenderer headerRenderer =
            table.getTableHeader().getDefaultRenderer();

        for (int i = 0; i < 3; i++) {
            column = table.getColumnModel().getColumn(i);

            comp = headerRenderer.getTableCellRendererComponent(
                                 null, column.getHeaderValue(),
                                 false, false, 0, 0);
            headerWidth = comp.getPreferredSize().width;

            comp = table.getDefaultRenderer(model.getColumnClass(i)).
                             getTableCellRendererComponent(
                                 table, longValues[i],
                                 false, false, 0, i);
            cellWidth = comp.getPreferredSize().width;
            column.setPreferredWidth(Math.max(headerWidth, cellWidth));
        }
    }
    
    private void initColumnSizesFunc(JTable table) {
    	FuncionariosTableModel model = (FuncionariosTableModel) table.getModel();
        TableColumn column = null;
        Component comp = null;
        int headerWidth = 0;
        int cellWidth = 0;
        Object[] longValues = model.longValues;
        TableCellRenderer headerRenderer =
            table.getTableHeader().getDefaultRenderer();

        for (int i = 0; i < 3; i++) {
            column = table.getColumnModel().getColumn(i);

            comp = headerRenderer.getTableCellRendererComponent(
                                 null, column.getHeaderValue(),
                                 false, false, 0, 0);
            headerWidth = comp.getPreferredSize().width;

            comp = table.getDefaultRenderer(model.getColumnClass(i)).
                             getTableCellRendererComponent(
                                 table, longValues[i],
                                 false, false, 0, i);
            cellWidth = comp.getPreferredSize().width;
            column.setPreferredWidth(Math.max(headerWidth, cellWidth));
        }
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		if ("Atualizar".equals(e.getActionCommand())) {
			NumberFormat paymentFormat = NumberFormat.getCurrencyInstance();
			try {
				this.banco.atualizarContas(paymentFormat.parse(this.txtTaxa.getText()).doubleValue());
				this.removeAll();
				this.initialize();
				this.repaint();
				
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		} 
	
	}
	
	public void initialize() {
		banco = new Banco();
		this.listaClientes = banco.getClientes();
		this.listaFuncionarios = banco.getFuncionarios();
		
		NumberFormat paymentFormat = NumberFormat.getCurrencyInstance();
		
		JPanel panelBuscaCliente = new JPanel();
		panelBuscaCliente.setBorder(null);
		add(panelBuscaCliente, BorderLayout.CENTER);
		SpringLayout sl_panelBuscaCliente = new SpringLayout();
		panelBuscaCliente.setLayout(sl_panelBuscaCliente);

		JPanel panelDadosPessoais = new JPanel();
		sl_panelBuscaCliente.putConstraint(SpringLayout.NORTH, panelDadosPessoais, 10, SpringLayout.NORTH, panelBuscaCliente);
		sl_panelBuscaCliente.putConstraint(SpringLayout.WEST, panelDadosPessoais, 10, SpringLayout.WEST, panelBuscaCliente);
		sl_panelBuscaCliente.putConstraint(SpringLayout.SOUTH, panelDadosPessoais, -10, SpringLayout.SOUTH, panelBuscaCliente);
		sl_panelBuscaCliente.putConstraint(SpringLayout.EAST, panelDadosPessoais, -10, SpringLayout.EAST, panelBuscaCliente);
		panelDadosPessoais.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Dados financeiros", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelBuscaCliente.add(panelDadosPessoais);
		SpringLayout sl_panelDadosPessoais = new SpringLayout();
		panelDadosPessoais.setLayout(sl_panelDadosPessoais);

		panelPagamentos = new JPanel();
		sl_panelDadosPessoais.putConstraint(SpringLayout.NORTH, panelPagamentos, 400, SpringLayout.NORTH, panelDadosPessoais);
		sl_panelDadosPessoais.putConstraint(SpringLayout.WEST, panelPagamentos, 16, SpringLayout.WEST, panelDadosPessoais);
		sl_panelDadosPessoais.putConstraint(SpringLayout.SOUTH, panelPagamentos, -62, SpringLayout.SOUTH, panelDadosPessoais);
		panelPagamentos.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Pagamentos", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelDadosPessoais.add(panelPagamentos);
		SpringLayout sl_panelPagamentos = new SpringLayout();
		panelPagamentos.setLayout(sl_panelPagamentos);
		
		JPanel panelReceitas = new JPanel();
		sl_panelDadosPessoais.putConstraint(SpringLayout.EAST, panelPagamentos, 0, SpringLayout.EAST, panelReceitas);
		sl_panelDadosPessoais.putConstraint(SpringLayout.NORTH, panelReceitas, 10, SpringLayout.NORTH, panelDadosPessoais);
		sl_panelDadosPessoais.putConstraint(SpringLayout.WEST, panelReceitas, 16, SpringLayout.WEST, panelDadosPessoais);
		sl_panelDadosPessoais.putConstraint(SpringLayout.SOUTH, panelReceitas, -10, SpringLayout.NORTH, panelPagamentos);
		sl_panelDadosPessoais.putConstraint(SpringLayout.EAST, panelReceitas, -10, SpringLayout.EAST, panelDadosPessoais);
		panelReceitas.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Receita", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelDadosPessoais.add(panelReceitas);
		SpringLayout sl_panelReceitas = new SpringLayout();
		panelReceitas.setLayout(sl_panelReceitas);
		
		
		tableClientes = new JTable();
		ClientesTableModel clientesTableModel = new ClientesTableModel(listaClientes);
		tableClientes.setModel(clientesTableModel);
		initColumnSizesClientes(tableClientes);

		tableClientes.setFont(new Font("Arial", Font.PLAIN, 12));
		sl_panelReceitas.putConstraint(SpringLayout.NORTH, tableClientes, 10, SpringLayout.NORTH, panelReceitas);
		sl_panelReceitas.putConstraint(SpringLayout.WEST, tableClientes, 10, SpringLayout.WEST, panelReceitas);
		sl_panelReceitas.putConstraint(SpringLayout.SOUTH, tableClientes, -10, SpringLayout.SOUTH, panelReceitas);
		sl_panelReceitas.putConstraint(SpringLayout.EAST, tableClientes, -10, SpringLayout.EAST, panelReceitas);
		JScrollPane scrollPaneClientes = new JScrollPane(tableClientes);
		sl_panelReceitas.putConstraint(SpringLayout.NORTH, scrollPaneClientes, 10, SpringLayout.NORTH, panelReceitas);
		sl_panelReceitas.putConstraint(SpringLayout.WEST, scrollPaneClientes, 10, SpringLayout.WEST, panelReceitas);
		sl_panelReceitas.putConstraint(SpringLayout.SOUTH, scrollPaneClientes, -40, SpringLayout.SOUTH, panelReceitas);
		sl_panelReceitas.putConstraint(SpringLayout.EAST, scrollPaneClientes, -10, SpringLayout.EAST, panelReceitas);
		tableClientes.setFillsViewportHeight(true);
		panelReceitas.add(scrollPaneClientes);
		
		JLabel lblTotal = new JLabel("Total");
		lblTotal.setFont(new Font("Arial", Font.PLAIN, 16));
		panelReceitas.add(lblTotal);
		
		txtTotalReceita = new JFormattedTextField(paymentFormat);
		txtTotalReceita.setFont(new Font("Arial", Font.PLAIN, 15));
		sl_panelReceitas.putConstraint(SpringLayout.NORTH, lblTotal, 1, SpringLayout.NORTH, txtTotalReceita);
		sl_panelReceitas.putConstraint(SpringLayout.EAST, lblTotal, -6, SpringLayout.WEST, txtTotalReceita);
		txtTotalReceita.setEditable(false);
		sl_panelReceitas.putConstraint(SpringLayout.NORTH, txtTotalReceita, 6, SpringLayout.SOUTH, scrollPaneClientes);
		sl_panelReceitas.putConstraint(SpringLayout.EAST, txtTotalReceita, 0, SpringLayout.EAST, scrollPaneClientes);
		panelReceitas.add(txtTotalReceita);
		txtTotalReceita.setColumns(10);
		
		JButton btnAtualizarContasCom = new JButton("Aplicar taxa de manuten\u00E7\u00E3o");
		sl_panelReceitas.putConstraint(SpringLayout.NORTH, btnAtualizarContasCom, -3, SpringLayout.NORTH, lblTotal);
		sl_panelReceitas.putConstraint(SpringLayout.WEST, btnAtualizarContasCom, 0, SpringLayout.WEST, scrollPaneClientes);
		btnAtualizarContasCom.setFont(new Font("Arial", Font.PLAIN, 15));
		panelReceitas.add(btnAtualizarContasCom);
		
		JLabel lblTotalLquido = new JLabel("Total l\u00EDquido");
		lblTotalLquido.setFont(new Font("Arial", Font.PLAIN, 25));
		panelDadosPessoais.add(lblTotalLquido);
		
		txtTotalLiquido = new JFormattedTextField(paymentFormat);
		txtTotalLiquido.setEditable(false);
		sl_panelDadosPessoais.putConstraint(SpringLayout.NORTH, txtTotalLiquido, 6, SpringLayout.SOUTH, panelPagamentos);
		sl_panelDadosPessoais.putConstraint(SpringLayout.NORTH, lblTotalLquido, 3, SpringLayout.NORTH, txtTotalLiquido);
		sl_panelDadosPessoais.putConstraint(SpringLayout.EAST, lblTotalLquido, -6, SpringLayout.WEST, txtTotalLiquido);
		sl_panelDadosPessoais.putConstraint(SpringLayout.EAST, txtTotalLiquido, 0, SpringLayout.EAST, panelPagamentos);
		
		
		tableFuncionarios = new JTable();
		FuncionariosTableModel funcionariosTableModel = new FuncionariosTableModel(listaFuncionarios);
		tableFuncionarios.setModel(funcionariosTableModel);
		initColumnSizesFunc(tableFuncionarios);
		tableFuncionarios.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JScrollPane scrollPaneFuncionarios = new JScrollPane(tableFuncionarios);
		sl_panelPagamentos.putConstraint(SpringLayout.NORTH, scrollPaneFuncionarios, 10, SpringLayout.NORTH, panelPagamentos);
		sl_panelPagamentos.putConstraint(SpringLayout.WEST, scrollPaneFuncionarios, 10, SpringLayout.WEST, panelPagamentos);
		sl_panelPagamentos.putConstraint(SpringLayout.SOUTH, scrollPaneFuncionarios, -40, SpringLayout.SOUTH, panelPagamentos);
		sl_panelPagamentos.putConstraint(SpringLayout.EAST, scrollPaneFuncionarios, -10, SpringLayout.EAST, panelPagamentos);
		panelPagamentos.add(scrollPaneFuncionarios);
		
		
		txtTotalPagamentos = new JFormattedTextField(paymentFormat);
		sl_panelPagamentos.putConstraint(SpringLayout.NORTH, txtTotalPagamentos, 6, SpringLayout.SOUTH, scrollPaneFuncionarios);
		sl_panelPagamentos.putConstraint(SpringLayout.EAST, txtTotalPagamentos, 0, SpringLayout.EAST, scrollPaneFuncionarios);
		txtTotalPagamentos.setFont(new Font("Arial", Font.PLAIN, 15));
		txtTotalPagamentos.setEditable(false);
		txtTotalPagamentos.setColumns(10);
		panelPagamentos.add(txtTotalPagamentos);
		
		JLabel label = new JLabel("Total");
		sl_panelPagamentos.putConstraint(SpringLayout.NORTH, label, 2, SpringLayout.NORTH, txtTotalPagamentos);
		sl_panelPagamentos.putConstraint(SpringLayout.EAST, label, -6, SpringLayout.WEST, txtTotalPagamentos);
		label.setFont(new Font("Arial", Font.PLAIN, 16));
		panelPagamentos.add(label);
		txtTotalLiquido.setFont(new Font("Arial", Font.PLAIN, 25));
		panelDadosPessoais.add(txtTotalLiquido);
		txtTotalLiquido.setColumns(10);

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
		
		
		this.txtTotalReceita.setText(paymentFormat.format(banco.calcularSaldoTotal()));
		this.txtTotalPagamentos.setText(paymentFormat.format(banco.calcularTotalDeGastos()));
		this.txtTotalLiquido.setText(paymentFormat.format(banco.calcularSaldoTotal()-banco.calcularTotalDeGastos()));
		
		btnAtualizarContasCom.setActionCommand("Atualizar");
		btnAtualizarContasCom.addActionListener(this);
		
		JLabel lblTaxa = new JLabel("Taxa");
		sl_panelReceitas.putConstraint(SpringLayout.WEST, lblTaxa, 6, SpringLayout.EAST, btnAtualizarContasCom);
		sl_panelReceitas.putConstraint(SpringLayout.SOUTH, lblTaxa, 0, SpringLayout.SOUTH, lblTotal);
		lblTaxa.setFont(new Font("Arial", Font.PLAIN, 16));
		panelReceitas.add(lblTaxa);
		
		txtTaxa = new JFormattedTextField(paymentFormat);
		sl_panelReceitas.putConstraint(SpringLayout.NORTH, txtTaxa, 6, SpringLayout.SOUTH, scrollPaneClientes);
		sl_panelReceitas.putConstraint(SpringLayout.WEST, txtTaxa, 6, SpringLayout.EAST, lblTaxa);
		sl_panelReceitas.putConstraint(SpringLayout.EAST, txtTaxa, 78, SpringLayout.EAST, lblTaxa);
		txtTaxa.setText("R$ 1,00");
		txtTaxa.setFont(new Font("Arial", Font.PLAIN, 15));
		txtTaxa.setColumns(10);
		panelReceitas.add(txtTaxa);
	}

	public Banco getBanco() {
		return banco;
	}

	public JPanel getPanelPagamentos() {
		return panelPagamentos;
	}

	public JFormattedTextField getTxtTotalLiquido() {
		return txtTotalLiquido;
	}

	public JTable getTableClientes() {
		return tableClientes;
	}

	public JTable getTableFuncionarios() {
		return tableFuncionarios;
	}

	public List<Cliente> getListaClientes() {
		return listaClientes;
	}

	public List<Funcionario> getListaFuncionarios() {
		return listaFuncionarios;
	}

	public JFormattedTextField getTxtTotalReceita() {
		return txtTotalReceita;
	}

	public JFormattedTextField getTxtTotalPagamentos() {
		return txtTotalPagamentos;
	}

	public JFormattedTextField getTxtTaxa() {
		return txtTaxa;
	}
	
	
}

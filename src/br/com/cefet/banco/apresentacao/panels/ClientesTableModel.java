package br.com.cefet.banco.apresentacao.panels;

import java.text.NumberFormat;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import br.com.cefet.banco.negocio.Cliente;
import br.com.cefet.banco.negocio.Conta;
import br.com.cefet.banco.persistencia.bd.ClienteDAO;
import br.com.cefet.banco.persistencia.bd.ContaDAO;
import br.com.cefet.banco.persistencia.bd.FuncionarioDAO;

public class ClientesTableModel extends AbstractTableModel{

	private String[] colunasClientes = {"nº","Cliente","Tipo de conta","Saldo"};
	private Object[][] dataClientes;
	public final Object[] longValues = {"999", 
						"Fulano Fulano Fulano Fulano Fulano Fulano Fulano Fulano Fulano Fulano Fulano Fulano Fulano Fulano Fulano Fulano Fulano Fulano Fulano",
						"Conta corrente",
						"R$ 1.000.000,00"};
	
	public ClientesTableModel(List<Cliente> listaClientes) {
		NumberFormat paymentFormat = NumberFormat.getCurrencyInstance();
		ContaDAO contaDao = new ContaDAO();
		this.dataClientes = new Object[listaClientes.size()][4];
		for(int i = 0; i < listaClientes.size();i++) {
			dataClientes[i][0] = listaClientes.get(i).getId();
			dataClientes[i][1] = listaClientes.get(i).getNome();
			Conta conta = contaDao.getContaDeCliente(listaClientes.get(i));
			if(conta != null) {
				dataClientes[i][2] = conta.getTipoStr();
				dataClientes[i][3] = paymentFormat.format(conta.getSaldo());
			} else {
				dataClientes[i][2] = "Conta inválida";
				dataClientes[i][3] = "R$ 0,00";
			}
		}
		
	}

	@Override
	public String getColumnName(int c) {
		return this.colunasClientes[c];
	}
	
	/*
	 * Don't need to implement this method unless your table's
	 * editable.
	 */
	public boolean isCellEditable(int row, int col) {
		//Note that the data/cell address is constant,
		//no matter where the cell appears onscreen.
		return false;
	}



	@Override
	public int getColumnCount() {
		return 4;
	}


	@Override
	public int getRowCount() {
		return dataClientes.length;
	}


	@Override
	public Object getValueAt(int i, int j) {
		return dataClientes[i][j];
	}
	

}

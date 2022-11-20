package br.com.cefet.banco.apresentacao.panels;

import java.text.NumberFormat;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.cefet.banco.negocio.Funcionario;
import br.com.cefet.banco.persistencia.bd.ContaDAO;

public class FuncionariosTableModel extends AbstractTableModel{

	private String[] colunasFuncionarios = {"nº","Funcionario","Cargo","Departamento","Salário"};
	private Object[][] dataFuncionarios;
	public final Object[] longValues = {"999", 
						"Fulano Fulano Fulano Fulano Fulano Fulano Fulano Fulano Fulano Fulano Fulano Fulano Fulano Fulano Fulano Fulano Fulano Fulano Fulano",
						"Cargo Cargo Cargo",
						"Departamento Departamento",
						"R$ 1.000.000,00"};
	
	public FuncionariosTableModel(List<Funcionario> listaFuncionarios) {
		NumberFormat paymentFormat = NumberFormat.getCurrencyInstance();
		this.dataFuncionarios = new Object[listaFuncionarios.size()][5];
		for(int i = 0; i < listaFuncionarios.size();i++) {
			dataFuncionarios[i][0] = listaFuncionarios.get(i).getId();
			dataFuncionarios[i][1] = listaFuncionarios.get(i).getNome();
			dataFuncionarios[i][2] = listaFuncionarios.get(i).getCargoStr();
			dataFuncionarios[i][3] = listaFuncionarios.get(i).getDepartamento();
			dataFuncionarios[i][4] = paymentFormat.format(listaFuncionarios.get(i).getSalario());

		}
		
	}
	
	@Override
	public String getColumnName(int c) {
		return this.colunasFuncionarios[c];
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
		return 5;
	}


	@Override
	public int getRowCount() {
		return dataFuncionarios.length;
	}


	@Override
	public Object getValueAt(int i, int j) {
		return dataFuncionarios[i][j];
	}
	

}

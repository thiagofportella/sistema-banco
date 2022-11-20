package br.com.cefet.banco.apresentacao.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class ClockLabel extends JLabel implements ActionListener {
	
	static SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 

	/**
	 * 
	 */
	private static final long serialVersionUID = -6134318420302891152L;

	public ClockLabel() {	   
		super("" + fmt.format(new Date()));
		Timer t = new Timer(1000, this);
		t.start();
	}

	public void actionPerformed(ActionEvent ae) {
		setText(fmt.format(new Date()));
	}
}


package br.com.cefet.banco.apresentacao.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.SystemColor;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import javax.swing.DropMode;

public class StatusBar extends JPanel {

	String nomeUsuario;
	
	private static final long serialVersionUID = 5909184826271605261L;

	public StatusBar(String nome) {
		this.nomeUsuario = nome;
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(485, 23));

		JPanel rightPanel = new JPanel(new FlowLayout());
		rightPanel.setOpaque(false);
		add(rightPanel, BorderLayout.EAST);	
		
				ClockLabel clock = new ClockLabel();
				clock.setBorder(null);
				rightPanel.add(clock);
				clock.setHorizontalAlignment(SwingConstants.RIGHT);
				clock.setFont(new Font("Arial", Font.PLAIN, 15));
				
				JPanel panel = new JPanel();
				rightPanel.add(panel, BorderLayout.EAST);
				panel.setLayout(new BorderLayout(0, 0));
				JLabel label = new JLabel(new AngledLinesWindowsCornerIcon());
				panel.add(label, BorderLayout.SOUTH);	
				label.setVerticalAlignment(SwingConstants.BOTTOM);

		JPanel centerPanel = new JPanel(new FlowLayout());
		centerPanel.setOpaque(false);
		add(centerPanel, BorderLayout.WEST);
		
		JLabel lblNomeDoUsuario = new JLabel(nomeUsuario);
		lblNomeDoUsuario.setHorizontalAlignment(SwingConstants.LEFT);
		lblNomeDoUsuario.setFont(new Font("Arial", Font.PLAIN, 15));
		centerPanel.add(lblNomeDoUsuario);
		
		setBackground(SystemColor.control);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		int y = 0;
		g.setColor(new Color(156, 154, 140));
		g.drawLine(0, y, getWidth(), y);
		y++;
		g.setColor(new Color(196, 194, 183));
		g.drawLine(0, y, getWidth(), y);
		y++;
		g.setColor(new Color(218, 215, 201));
		g.drawLine(0, y, getWidth(), y);
		y++;
		g.setColor(new Color(233, 231, 217));
		g.drawLine(0, y, getWidth(), y);

		y = getHeight() - 3;
		g.setColor(new Color(233, 232, 218));
		g.drawLine(0, y, getWidth(), y);
		y++;
		g.setColor(new Color(233, 231, 216));
		g.drawLine(0, y, getWidth(), y);
		y = getHeight() - 1;
		g.setColor(new Color(221, 221, 220));
		g.drawLine(0, y, getWidth(), y);

	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}
	
	

}

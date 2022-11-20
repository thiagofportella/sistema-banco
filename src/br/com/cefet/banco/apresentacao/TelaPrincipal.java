package br.com.cefet.banco.apresentacao;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.openswing.swing.mdi.client.Clock;

import br.com.cefet.banco.apresentacao.panels.StatusBar;
import br.com.cefet.banco.negocio.Autenticavel;
import br.com.cefet.banco.negocio.Caixa;
import br.com.cefet.banco.negocio.Cliente;
import br.com.cefet.banco.negocio.Diretor;
import br.com.cefet.banco.negocio.Funcionario;
import br.com.cefet.banco.negocio.Gerente;
import br.com.cefet.banco.util.BancoUtil;

import java.awt.Font;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import java.awt.Dimension;

public class TelaPrincipal implements ActionListener{

	JFrame frmSistemaBancrio;
	private Autenticavel usuarioLogado;
	private String nomeUsuario;
	private JPanel panelPrincipal;

	/**
	 * Create the application.
	 */
	public TelaPrincipal(Autenticavel a) {
		this.usuarioLogado = a;
		this.nomeUsuario = BancoUtil.pegarNomeUsuario(a);
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSistemaBancrio = new JFrame();
		frmSistemaBancrio.setMinimumSize(new Dimension(800, 600));
		frmSistemaBancrio.setTitle("Sistema Banc\u00E1rio");
		frmSistemaBancrio.setResizable(true);
		frmSistemaBancrio.setBounds(100, 100, 450, 300);
		frmSistemaBancrio.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSistemaBancrio.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frmSistemaBancrio.setAlwaysOnTop(true);

		JMenuBar menuBar = new JMenuBar();
		frmSistemaBancrio.setJMenuBar(menuBar);

		JMenu mnUsuario = new JMenu("Conta");
		mnUsuario.setFont(new Font("Arial", Font.PLAIN, 15));
		menuBar.add(mnUsuario);

		JMenuItem mntmTrocarUsuario = new JMenuItem("Trocar usuário");
		mntmTrocarUsuario.setFont(new Font("Arial", Font.PLAIN, 15));
		mnUsuario.add(mntmTrocarUsuario);
		mntmTrocarUsuario.setActionCommand("Trocar usuário");
		
		JMenuItem mntmAlterarSenha = new JMenuItem("Alterar senha");
		mntmAlterarSenha.setFont(new Font("Arial", Font.PLAIN, 15));
		mnUsuario.add(mntmAlterarSenha);
		mntmAlterarSenha.setActionCommand("Alterar senha");
		mntmAlterarSenha.addActionListener(this);

		JMenuItem mntmSair = new JMenuItem("Sair");
		mntmSair.setFont(new Font("Arial", Font.PLAIN, 15));
		mnUsuario.add(mntmSair);
		mntmSair.setActionCommand("Sair");
		mntmSair.addActionListener(this);
		mntmTrocarUsuario.addActionListener(this);


		panelPrincipal = new JPanel();
		frmSistemaBancrio.getContentPane().add(panelPrincipal, BorderLayout.CENTER);
		panelPrincipal.setLayout(new BorderLayout(0, 0));

		StatusBar statusBar = new StatusBar(nomeUsuario);
		frmSistemaBancrio.getContentPane().add(statusBar, BorderLayout.SOUTH);

		panelPrincipal.setVisible(true);


		// Dependendo do tipo de usuário, personaliza a tela e as ações
		if(BancoUtil.usuarioEhCliente(usuarioLogado)) {
			// Inserindo painel do cliente, se for o caso
			PainelCliente painelCliente = new PainelCliente((Cliente) usuarioLogado);
			panelPrincipal.add(painelCliente, BorderLayout.CENTER);

		} else if(BancoUtil.usuarioEhCaixa(usuarioLogado)) {
			// Inserindo painel do cliente, se for o caso
			PainelCaixa painelCaixa = new PainelCaixa();
			panelPrincipal.add(painelCaixa, BorderLayout.CENTER);

		} 
		if(BancoUtil.possuiFuncionalidadesGerente(usuarioLogado)) {
			JMenu mnGerente = new JMenu("Gerenciar clientes");
			mnGerente.setFont(new Font("Arial", Font.PLAIN, 15));
			menuBar.add(mnGerente);

			JMenuItem mntmCadastrarCliente = new JMenuItem("Cadastrar cliente");
			mntmCadastrarCliente.setFont(new Font("Arial", Font.PLAIN, 15));
			mnGerente.add(mntmCadastrarCliente);
			mntmCadastrarCliente.setActionCommand("Cadastrar cliente");
			mntmCadastrarCliente.addActionListener(this);

			JMenuItem mntmRemoverCliente = new JMenuItem("Excluir cliente");
			mntmRemoverCliente.setFont(new Font("Arial", Font.PLAIN, 15));
			mnGerente.add(mntmRemoverCliente);
			mntmRemoverCliente.setActionCommand("Excluir cliente");
			mntmRemoverCliente.addActionListener(this);

			JMenuItem mntmConsultarCliente = new JMenuItem("Consultar cliente");
			mntmConsultarCliente.setFont(new Font("Arial", Font.PLAIN, 15));
			mnGerente.add(mntmConsultarCliente);
			mntmConsultarCliente.setActionCommand("Consultar cliente");
			mntmConsultarCliente.addActionListener(this);
			
			if(BancoUtil.usuarioEhGerente(usuarioLogado)) {
				JMenu mnDiretor = new JMenu("Gerenciar funcionários");
				mnDiretor.setFont(new Font("Arial", Font.PLAIN, 15));
				menuBar.add(mnDiretor);
				
				JMenuItem mntmConsultarFuncionario = new JMenuItem("Consultar funcionário");
				mntmConsultarFuncionario.setFont(new Font("Arial", Font.PLAIN, 15));
				mnDiretor.add(mntmConsultarFuncionario);
				mntmConsultarFuncionario.setActionCommand("Consultar funcionário");
				mntmConsultarFuncionario.addActionListener(this);
			}
		}
		if(BancoUtil.possuiFuncionalidadesDiretor(usuarioLogado)) {
			JMenu mnDiretor = new JMenu("Gerenciar funcionários");
			mnDiretor.setFont(new Font("Arial", Font.PLAIN, 15));
			menuBar.add(mnDiretor);

			JMenuItem mntmCadastrarFuncionario = new JMenuItem("Cadastrar funcionário");
			mntmCadastrarFuncionario.setFont(new Font("Arial", Font.PLAIN, 15));
			mnDiretor.add(mntmCadastrarFuncionario);
			mntmCadastrarFuncionario.setActionCommand("Cadastrar funcionário");
			mntmCadastrarFuncionario.addActionListener(this);

			JMenuItem mntmConsultarFuncionario = new JMenuItem("Consultar funcionário");
			mntmConsultarFuncionario.setFont(new Font("Arial", Font.PLAIN, 15));
			mnDiretor.add(mntmConsultarFuncionario);
			mntmConsultarFuncionario.setActionCommand("Consultar funcionário");
			mntmConsultarFuncionario.addActionListener(this);
			
			JMenuItem mntmExcluirFuncionario = new JMenuItem("Excluir funcionário");
			mntmExcluirFuncionario.setFont(new Font("Arial", Font.PLAIN, 15));
			mnDiretor.add(mntmExcluirFuncionario);
			mntmExcluirFuncionario.setActionCommand("Excluir funcionário");
			mntmExcluirFuncionario.addActionListener(this);
			
			JMenu mnFinanceiro = new JMenu("Financeiro");
			mnFinanceiro.setFont(new Font("Arial", Font.PLAIN, 15));
			menuBar.add(mnFinanceiro);

			JMenuItem mntmRelatorioGeral = new JMenuItem("Relatório geral");
			mntmRelatorioGeral.setFont(new Font("Arial", Font.PLAIN, 15));
			mnFinanceiro.add(mntmRelatorioGeral);
			mntmRelatorioGeral.setActionCommand("Relatório geral");
			mntmRelatorioGeral.addActionListener(this);
		}

		JMenu mnAjuda = new JMenu("Ajuda");
		mnAjuda.setFont(new Font("Arial", Font.PLAIN, 15));
		menuBar.add(mnAjuda);

		JMenuItem mntmSobre = new JMenuItem("Sobre");
		mntmSobre.setFont(new Font("Arial", Font.PLAIN, 15));
		mnAjuda.add(mntmSobre);
		mntmSobre.setActionCommand("Sobre");
		mntmSobre.addActionListener(this);
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		if ("Trocar usuário".equals(e.getActionCommand())) {
			this.frmSistemaBancrio.dispose();
			TelaLogin window = new TelaLogin();
			window.frmLogin.setVisible(true);
		} else if ("Alterar senha".equals(e.getActionCommand())) {
			TelaAlterarSenha telaAlterarSenha = new TelaAlterarSenha(this.usuarioLogado);
			telaAlterarSenha.frmLogin.setVisible(true);
		} else if ("Cadastrar cliente".equals(e.getActionCommand())) {
			panelPrincipal.removeAll();
			PainelCadastrarCliente painelCadastrarCliente = new PainelCadastrarCliente();
			panelPrincipal.add(painelCadastrarCliente, BorderLayout.CENTER);
		} else if ("Excluir cliente".equals(e.getActionCommand())) {
			panelPrincipal.removeAll();
			PainelExcluirCliente painelExcluirCliente = new PainelExcluirCliente();
			panelPrincipal.add(painelExcluirCliente, BorderLayout.CENTER);
		} else if ("Consultar cliente".equals(e.getActionCommand())) {
			panelPrincipal.removeAll();
			PainelConsultarCliente painelConsultarCliente = new PainelConsultarCliente();
			panelPrincipal.add(painelConsultarCliente, BorderLayout.CENTER);
		} else if ("Cadastrar funcionário".equals(e.getActionCommand())) {
			panelPrincipal.removeAll();
			PainelCadastrarFuncionario painelCadastrarFuncionario = new PainelCadastrarFuncionario(false);
			panelPrincipal.add(painelCadastrarFuncionario, BorderLayout.CENTER);
		} else if ("Consultar funcionário".equals(e.getActionCommand())) {
			panelPrincipal.removeAll();
			PainelConsultarFuncionario painelConsultarFuncionario = new PainelConsultarFuncionario(this.usuarioLogado);
			panelPrincipal.add(painelConsultarFuncionario, BorderLayout.CENTER);
		} else if ("Excluir funcionário".equals(e.getActionCommand())) {
			panelPrincipal.removeAll();
			PainelExcluirFuncionario painelExcluirFuncionario = new PainelExcluirFuncionario((Funcionario) this.usuarioLogado);
			panelPrincipal.add(painelExcluirFuncionario, BorderLayout.CENTER);
		} else if ("Relatório geral".equals(e.getActionCommand())) {
			panelPrincipal.removeAll();
			PainelRelatorioGeral painelRelatorioGeral = new PainelRelatorioGeral();
			panelPrincipal.add(painelRelatorioGeral, BorderLayout.CENTER);
		} else if ("Sobre".equals(e.getActionCommand())) {
			JOptionPane.showMessageDialog(this.frmSistemaBancrio, "Sistema Banc\u00E1rio - v0.1beta\r\n\r\nSistema Referente ao Trabalho 1 da disciplina \"Teste e Manuten\u00E7\u00E3o de Software\".\r\n\r\nProfessor Marco Kappel\r\nCefet/RJ", "Sobre", JOptionPane.INFORMATION_MESSAGE);
		} else if ("Sair".equals(e.getActionCommand())) {
			this.frmSistemaBancrio.dispose();
		} else {
			this.frmSistemaBancrio.dispose();
		}

	}
	
	

	public JFrame getFrmSistemaBancrio() {
		return frmSistemaBancrio;
	}

	public Autenticavel getUsuarioLogado() {
		return usuarioLogado;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public JPanel getPanelPrincipal() {
		return panelPrincipal;
	}
	
	

}

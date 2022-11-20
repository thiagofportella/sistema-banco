package br.com.cefet.banco.negocio;

import java.util.Iterator;
import java.util.List;

public class EfetuarAtualizacao implements Runnable{
	
	private List<ContaCorrente> contas;
	private AtualizadorDeContas atualizador;
	
	public EfetuarAtualizacao(List<ContaCorrente> contas,
			AtualizadorDeContas atualizador) {
		this.contas = contas;
		this.atualizador = atualizador;
	}
	
	public void run() {
		Iterator<ContaCorrente> it = contas.iterator();
		while (it.hasNext()) {
			System.out.println("Thread EfetuarAtualizacao rodando...");
			ContaCorrente cc = it.next();
			atualizador.roda(cc);
		}
	}

	public List<ContaCorrente> getContas() {
		return contas;
	}

	public AtualizadorDeContas getAtualizador() {
		return atualizador;
	}
	
	
	
}



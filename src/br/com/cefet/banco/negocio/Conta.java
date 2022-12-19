package br.com.cefet.banco.negocio;

import br.com.cefet.banco.exceptions.DepositoInvalidoException;
import br.com.cefet.banco.exceptions.SaldoInsuficienteException;

/**
 * @author marco
 *
 */
public abstract class Conta implements Comparable<Conta>{
	private long id;
	private Cliente titular;
	protected double saldo;
	private double limite;
	private int tipo; // 0 - corrente, 1 - poupanca

	private static int totalDeContas;


	public Conta(double saldoInicial, double limiteInicial){
		this.saldo = saldoInicial;
		this.limite = limiteInicial;

		Conta.totalDeContas = Conta.totalDeContas + 1; 
	}
	
	public Conta(double saldoInicial){
		this.saldo = saldoInicial;
		this.limite = 0;

		Conta.totalDeContas = Conta.totalDeContas + 1; 
	}

	public synchronized void sacar(double valor) throws SaldoInsuficienteException{
			if ((this.saldo + this.limite) >= valor) {
				this.saldo -= valor;
			} else {
				throw new SaldoInsuficienteException("Saldo insuficiente. Você só pode sacar "+
						this.getSaldo()+this.getLimite());
			}
	}

	public void depositar(double valor) throws DepositoInvalidoException {
		double novoSaldo = this.saldo+=valor;

		if (valor >= 0) { 
			this.saldo =novoSaldo;
		}else{
			throw new DepositoInvalidoException("Erro ao depositar");
		}
	}

	public void transferir(Conta contaDestino, double valor){
		try {
			this.sacar(valor);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		try {
			contaDestino.depositar(valor);
		} catch (DepositoInvalidoException e) {
			e.printStackTrace();
		}
	}

	public abstract void atualiza(double taxa);
	
	public Cliente getTitular() {
		return this.titular;
	}

	public void setTitular(Cliente titular) {
		this.titular = titular;
	}

	public double getSaldo() {
		return saldo;
	}

	public double getLimite() {
		return limite;
	}

	public static int getTotalDeContas() {
		return Conta.totalDeContas;
	}

	public void imprimirResumo() {
		System.out.println("Titular: "+this.titular.getNome());
		System.out.println("Valor: "+this.getSaldo());
		System.out.println("-----------------------");
	}

	@Override
	public int compareTo(Conta outraConta) {
		if (this.getSaldo()<outraConta.getSaldo())
			return -1;
		else if (this.getSaldo()>outraConta.getSaldo())
			return -1;
		else 
			return 0;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public void setLimite(double limite) {
		this.limite = limite;
	}
	
	public String getTipoStr() {
		if(tipo==0) {
			return "Conta corrente";
		} else {
			return "Conta poupança";
		}
	}
	
	
	
}

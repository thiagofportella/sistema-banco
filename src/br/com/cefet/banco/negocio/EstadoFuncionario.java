package br.com.cefet.banco.negocio;

public enum EstadoFuncionario {
	EM_EXERCICIO("Em exerc�cio"),
	EM_FERIAS("Em f�rias"),
	AFASTADO_POR_DOENCA("Afastado por doen�a"),
	APOSENTADO("Aposentado");
	
	private final String estado;
	
	private EstadoFuncionario(String nomeEstado) {
		this.estado = nomeEstado;
	}
	
	public String toString(){
		return this.estado;
	}

	public String getEstado() {
		return estado;
	}
	
	

}

package br.com.cefet.banco.negocio;

public enum EstadoFuncionario {
	EM_EXERCICIO("Em exercício"),
	EM_FERIAS("Em férias"),
	AFASTADO_POR_DOENCA("Afastado por doença"),
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

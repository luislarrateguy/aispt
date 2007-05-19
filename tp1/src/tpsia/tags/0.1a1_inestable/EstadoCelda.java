package tpsia.tp1;

public enum EstadoCelda {
	Desconocida(-1),
	Vacia (0),
	Comida (1),
	Enemigo (2);
	
	private final int valor;
	
	EstadoCelda(int x) {
		this.valor = x;
	}
	
	public int valor() {
		return this.valor;
	}
}

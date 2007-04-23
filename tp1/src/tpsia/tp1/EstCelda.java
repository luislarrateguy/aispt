package tpsia.tp1;

public enum EstCelda {
	Desconocida(-1),
	Vacia (0),
	Comida (1),
	Enemigo (2);
	
	private final int valor;
	EstCelda() {
		this.valor = -1;
	}
	EstCelda(int x) {
		this.valor = x;
	}
	public int valor() {
		return this.valor;
	}
}

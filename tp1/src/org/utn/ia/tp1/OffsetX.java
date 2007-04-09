package org.utn.ia.tp1;

public enum OffsetX {
	Igual (0),
	Izquierda (-1),
	Derecha (1);
	
	private final int valor;
	OffsetX(int x) {
		this.valor = x;
	}
	public int valor() {
		return this.valor;
	}
}

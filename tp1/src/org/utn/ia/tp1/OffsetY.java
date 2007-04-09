package org.utn.ia.tp1;

public enum OffsetY {
	Igual (0),
	Arriba (1),
	Abajo (-1);
	private final int valor;
	OffsetY(int x) {
		this.valor = x;
	}
	public int valor() {
		return this.valor;
	}
}

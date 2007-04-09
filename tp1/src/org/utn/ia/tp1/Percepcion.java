package org.utn.ia.tp1;

public class Percepcion {

	private Matriz celdasAdyacentes;
	private Float energia;
	private int posx;
	private int posy;

	public Percepcion(Matriz m, Float e, int posx, int posy) {
		this.celdasAdyacentes = m;
		this.energia = e;
		this.posx = posx;
		this.posy = posy;
	}
}
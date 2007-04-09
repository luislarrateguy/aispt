package org.utn.ia.tp1;


public class AvanzarArriba extends Avanzar {

	private static AvanzarArriba instancia;

	private AvanzarArriba() {
	}

	public void ejecutar(IAmbiente amb) {
		amb.moverPacman(OffsetX.Igual, OffsetY.Arriba);
	}

	static public IAccion getInstancia() {
		if (instancia == null) {
			instancia = new AvanzarArriba();
		}
		return instancia;
	}
}
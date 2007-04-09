package org.utn.ia.tp1;


public class AvanzarAbajo extends Avanzar {

	private static AvanzarAbajo instancia;

	private AvanzarAbajo() {
	}

	public void ejecutar(IAmbiente amb) {
		amb.moverPacman(OffsetX.Igual, OffsetY.Abajo);
	}

	static public IAccion getInstancia() {
		if (instancia == null) {
			instancia = new AvanzarAbajo();
		}
		return instancia;
	}
}
package tpsia.tp1.acciones;

import tpsia.tp1.IAmbiente;
import tpsia.tp1.OffsetX;
import tpsia.tp1.OffsetY;


public class AvanzarIzquierda extends Avanzar {

	private static AvanzarIzquierda instancia;

	private AvanzarIzquierda() {
	}

	public void ejecutar(IAmbiente amb) {
		amb.moverPacman(OffsetX.Izquierda, OffsetY.Igual);
	}

	static public IAccion getInstancia() {
		if (instancia == null) {
			instancia = new AvanzarIzquierda();
		}
		return instancia;
	}
}
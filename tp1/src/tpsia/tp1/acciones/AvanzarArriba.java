package tpsia.tp1.acciones;

import tpsia.tp1.IAmbiente;
import tpsia.tp1.OffsetX;
import tpsia.tp1.OffsetY;


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
	@Override
	public String getTipoAccion() {
		return "arriba";
	}
}
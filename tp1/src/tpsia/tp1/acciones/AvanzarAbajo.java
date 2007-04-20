package tpsia.tp1.acciones;

import tpsia.tp1.IAmbiente;
import tpsia.tp1.OffsetX;
import tpsia.tp1.OffsetY;


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
	@Override
	public String getTipoAccion() {
		return "abajo";
	}
}
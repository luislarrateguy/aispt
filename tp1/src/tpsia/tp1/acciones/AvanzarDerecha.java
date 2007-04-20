package tpsia.tp1.acciones;

import tpsia.tp1.IAmbiente;
import tpsia.tp1.OffsetX;
import tpsia.tp1.OffsetY;


public class AvanzarDerecha extends Avanzar {

	private static AvanzarDerecha instancia;

	private AvanzarDerecha() {
	}

	public void ejecutar(IAmbiente amb) {
		amb.moverPacman(OffsetX.Derecha, OffsetY.Igual);
	}

	static public IAccion getInstancia() {
		if (instancia == null) {
			instancia = new AvanzarDerecha();
		}
		return instancia;
	}
	@Override
	public String getTipoAccion() {
		return "derecha";
	}
}
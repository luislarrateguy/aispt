package tpsia.tp1;


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
}
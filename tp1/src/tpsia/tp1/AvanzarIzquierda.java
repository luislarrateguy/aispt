package tpsia.tp1;


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
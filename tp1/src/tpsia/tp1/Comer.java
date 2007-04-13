package tpsia.tp1;


/*
 * Singleton Notice
 */
public class Comer implements IAccion {

	private static Comer instancia;

	public void ejecutar(IAmbiente amb) {
		//TODO Modificar el ambiente de acuerdo a la
		// interfaz que definamos
	}

	public String getTipoAccion() {
		return "comer";
	}

	static public IAccion getInstancia() {
		if (instancia == null) {
			instancia = new Comer();
		}
		return instancia;
	}
}
package tpsia.tp1;


/*
 * Singleton Notice
 */
public class Pelear implements IAccion {

	private static Pelear instancia;

	public void ejecutar(IAmbiente amb) {
		//TODO Modificar el ambiente de acuerdo a la
		// interfaz que definamos
	}

	public String getTipoAccion() {
		return "pelear";
	}

	static public IAccion getInstancia() {
		if (instancia == null) {
			instancia = new Pelear();
		}
		return instancia;
	}
}
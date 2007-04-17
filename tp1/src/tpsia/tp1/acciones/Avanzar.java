package tpsia.tp1.acciones;

import tpsia.tp1.IAmbiente;


/*
 * Singleton Notice
 */
public abstract class Avanzar implements IAccion {


	public abstract void ejecutar(IAmbiente amb);

	public final String getTipoAccion() {
		return "avanzar";
	}
}
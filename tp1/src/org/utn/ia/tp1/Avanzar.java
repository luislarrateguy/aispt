package org.utn.ia.tp1;


/*
 * Singleton Notice
 */
public abstract class Avanzar implements IAccion {


	public abstract void ejecutar(IAmbiente amb);

	public final String getTipoAccion() {
		return "avanzar";
	}
}
package org.utn.ia.tp1;


public class Agente {

	private Estado est;

	public IAccion actuar(Percepcion p) {
		// TODO actualizar visión del ambiente con la nueva percepcion
		// TODO formular objetivo
		// TODO buscar accion
		IAccion a = AvanzarArriba.getInstancia(); // Obtenida de búsqueda
		// TODO ejecutar accion
		a.ejecutar(est.getVAmbiente());
		// TODO actualizar estado
		return a;
	}

}
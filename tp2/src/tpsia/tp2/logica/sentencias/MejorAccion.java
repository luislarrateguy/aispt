package tpsia.tp2.logica.sentencias;

import tpsia.tp2.acciones.Accion;

public class MejorAccion extends Sentencia {
	private Accion mejorAccion;
	
	public MejorAccion() {
		super();
		
		this.mejorAccion = null;
	}
	
	public MejorAccion(Accion a, int tiempo) {
		super(tiempo);
		
		this.mejorAccion = a;
	}

	public Accion getMejorAccion() {
		return mejorAccion;
	}

	public void setMejorAccion(Accion mejorAccion) {
		this.mejorAccion = mejorAccion;
	}
}

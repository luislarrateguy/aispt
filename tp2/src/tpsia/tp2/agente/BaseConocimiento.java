package tpsia.tp2.agente;

import tpsia.tp2.Percepcion;
import tpsia.tp2.acciones.Accion;

public class BaseConocimiento {

	public void decir(Percepcion p) {
		/* TODO: Se debería llamar a un método 'crearSentencia'
		 * que reciba la percepción y la transforme en... sentencias :D
		 */
	}
	
	public void decir(Accion a) {
		/* TODO: Se debería llamar a un método 'crearSentencia'
		 * que reciba la acción y la transforme en... sentencias :D
		 */
	}

	public Accion preguntar(int tiempo) {
		return null;
	}

	public boolean cumplioObjetivo() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean agenteVivo() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Crearía un objeto VisionAmbiente a partir de esta base
	 * de conocimiento.
	 * @return
	 */
	public VisionAmbiente getVisionAmbiente() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getEnergiaAgente() {
		// TODO Auto-generated method stub
		return 0;
	}

}

package tpsia.tp1.acciones;

import tpsia.tp1.Ambiente;

public class NoAccion extends Accion {
	private static NoAccion instancia;
	
	private NoAccion() {
	}
	
	public static NoAccion getInstancia() {
		if (instancia == null) {
			instancia = new NoAccion();
		}
		return instancia;
	}
	
	public boolean aplicable(Ambiente amb) {
		return false;
	}

	public void ejecutar(Ambiente amb) throws Exception {
		throw new Exception("Se intentó ejecutar la acción: NoAccion");
	}

	public int getCosto() {
		return 0;
	}

	public String getTipoAccion() {
		return "no accion";
	}
	public Class getClase() {
		return this.getClass();
	}
}

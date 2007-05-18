package tpsia.tp1.acciones;

import tpsia.tp1.Ambiente;
import tpsia.tp1.agente.Estado;

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
	
	@Override
	public boolean aplicable(Estado estado) {
		return true;
	}

	@Override
	public void ejecutar(Ambiente amb) throws Exception {
		throw new Exception("Se intentó ejecutar la acción: NoAccion");
	}

	@Override
	public int getCosto() {
		return 0;
	}

	@Override
	public String getTipoAccion() {
		return "no accion";
	}
	
	@Override
	public int getIdentificador() {
		return -1;
	}
}

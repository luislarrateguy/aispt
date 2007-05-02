package tpsia.tp1.acciones;

import tpsia.tp1.Ambiente;

public class NoAccion implements IAccion {
	private static NoAccion instancia;
	
	private NoAccion() {
	}
	
	public static NoAccion getInstancia() {
		if (instancia == null)
			instancia = new NoAccion();
		
		return instancia;
	}
	
	public boolean aplicable(Ambiente amb) {
		// TODO Auto-generated method stub
		return false;
	}

	public void ejecutar(Ambiente amb) {
		// TODO Auto-generated method stub
	}

	public int getCosto() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getTipoAccion() {
		// TODO Auto-generated method stub
		return "no accion";
	}

}

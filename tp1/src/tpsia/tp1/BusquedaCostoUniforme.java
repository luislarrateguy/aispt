package tpsia.tp1;

import java.util.ArrayList;

public class BusquedaCostoUniforme implements IBusqueda {

	public boolean cumpleObjetivo(Estado x) {
		// TODO Auto-generated method stub
		return false;
	}

	public ArrayList<IAccion> buscarSolucion(IProblema p) {
		// TODO buscar secuencia de acciones
		System.out.println("PACMAN: buscar accion");
		ArrayList<IAccion> l = new ArrayList<IAccion>();
		l.add(AvanzarDerecha.getInstancia());
		return l;
	}

}

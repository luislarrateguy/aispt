package tpsia.tp1.busqueda;

import java.util.ArrayList;

import tpsia.tp1.Estado;
import tpsia.tp1.acciones.AvanzarDerecha;
import tpsia.tp1.acciones.IAccion;

public class BusquedaCostoUniforme implements IBusqueda {

	public boolean cumpleObjetivo(Estado x) {
		// TODO Auto-generated method stub
		return false;
	}

	public ArrayList<IAccion> buscarSolucion(Estado p) {
		// TODO buscar secuencia de acciones
		System.out.println("PACMAN: buscar accion");
		ArrayList<IAccion> l = new ArrayList<IAccion>();
		l.add(AvanzarDerecha.getInstancia());
		return l;
	}

}

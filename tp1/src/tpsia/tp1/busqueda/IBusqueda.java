package tpsia.tp1.busqueda;

import java.util.ArrayList;

import tpsia.tp1.Estado;
import tpsia.tp1.acciones.IAccion;

public interface IBusqueda {
	boolean cumpleObjetivo(Estado x);

	ArrayList<IAccion> buscarSolucion(Estado p);
	
}

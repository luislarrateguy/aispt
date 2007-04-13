package tpsia.tp1;

import java.util.ArrayList;

public interface IBusqueda {
	boolean cumpleObjetivo(Estado x);

	ArrayList<IAccion> buscarSolucion(IProblema p);
	
}

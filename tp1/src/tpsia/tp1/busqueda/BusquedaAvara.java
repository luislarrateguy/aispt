package tpsia.tp1.busqueda;

import tpsia.tp1.agente.Estado;
import tpsia.tp1.agente.IObjetivo;

public class BusquedaAvara extends Busqueda {

	public BusquedaAvara(Estado estado, IObjetivo objetivo) {
		super(estado, objetivo);
	}
	
	@Override
	protected float calcularPrioridad(Nodo unNodo) {
		float heuristica = unNodo.getEstado().getAmbiente().cantidadCeldasDesconocidas();
		
		return heuristica;
	}

}

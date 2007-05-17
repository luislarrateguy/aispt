package tpsia.tp1.busqueda;

import tpsia.tp1.agente.Estado;
import tpsia.tp1.agente.IObjetivo;

public class BusquedaAmplitud extends Busqueda {

	public BusquedaAmplitud(Estado estado, IObjetivo objetivo) {
		super(estado, objetivo);
	}
	
	@Override
	protected float calcularPrioridad(Nodo unNodo) {
		// TODO Auto-generated method stub
		return (unNodo.getPadre().getPrioridadExpansion() + 1);
	}

}

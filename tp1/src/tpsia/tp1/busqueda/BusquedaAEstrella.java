package tpsia.tp1.busqueda;

import tpsia.tp1.acciones.Accion;
import tpsia.tp1.agente.Estado;
import tpsia.tp1.agente.IObjetivo;

public class BusquedaAEstrella extends Busqueda {

	public BusquedaAEstrella(Estado estado, IObjetivo objetivo) {
		super(estado, objetivo);
	}
	
	@Override
	protected float calcularPrioridad(Nodo unNodo) {
		/* Calculo el costo */
		float costo = unNodo.getPadre().getPrioridadExpansion() + unNodo.getAccionGeneradora().getCosto();
		
		/* Calculo la función heurística, que consiste en la cantidad de celdas que
		 * son desconocidas. */
		float heuristica = unNodo.getEstado().getAmbiente().cantidadCeldasDesconocidas();
		
		/* Retorno el valor de la función de evaluación = costo + heuristica */
		return (costo + heuristica);
	}
}

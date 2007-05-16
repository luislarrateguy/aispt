package tpsia.tp1.busqueda;

import java.util.ArrayList;
import java.util.Collection;
import java.util.PriorityQueue;

import tpsia.tp1.Logging;
import tpsia.tp1.acciones.Accion;
import tpsia.tp1.acciones.NoAccion;
import tpsia.tp1.agente.Estado;
import tpsia.tp1.agente.IObjetivo;
import tpsia.tp1.agente.VisionAmbiente;

public abstract class Busqueda {
	protected Estado estado;
	protected IObjetivo objetivo;
	
	/**
	 * La función de evaluación representa, en:
	 * - Estrategia de amplitud: costo (nodo padre + 1)
	 * - Estrategia de costo uniforme: costo (nodo padre + costo acción)
	 * - Estrategia A*: costo + heurística
	 * - etc...
	 * @param padre
	 * @param accionGeneradora
	 * @return
	 */
	protected abstract float calcularPrioridad(Nodo unNodo);
	
	public Busqueda(Estado estado, IObjetivo objetivo) {
		this.estado = estado;
		this.objetivo = objetivo;
	}
	
	public ArrayList<Accion> buscarSolucion() {
		Logging.logDebug("PACMAN: buscar accion");
		
		ArrayList<Accion> listaAcciones = new ArrayList<Accion>();
		PriorityQueue<Nodo> colaNodos = new PriorityQueue<Nodo>();
		ArrayList<VisionAmbiente> estadosAlcanzados = new ArrayList<VisionAmbiente>();
		
		Nodo nodoActual = new Nodo((Estado)this.estado.clone());
		
		/*
		 * Mientras no se cumple el objetivo en el nodo actual, seguimos expandiendo.
		 */
		while ( ! this.objetivo.cumpleObjetivo(nodoActual) ) {
			
			/* Vemos si el nodo actual ya fue inspeccionado. */
			if (!estadosAlcanzados.contains(nodoActual.getEstado().getAmbiente())) {
				
				/* Agrego el nodo actual a la lista de nodos ya inspeccionados. */
				estadosAlcanzados.add(nodoActual.getEstado().getAmbiente());
				
				for (Nodo n : this.expandir(nodoActual)) {
					colaNodos.add(n);
				}
			}
			
			if (!colaNodos.isEmpty())
				nodoActual = colaNodos.remove();
			else
				break;
		}
		
		/* FIXME: Hubo un problema cuando lo ejecuté una vez a en este punto.
		 * Devolvío NoAccion.
		 */
		/* nodoActual es un nodo que cumple con el objetivo. Entonces agregamos
		 * a la lista de acciones a devolver las acciones que fueron necesarias
		 * para llegar a este nodo objetivo. Notar que la lista de acciones está
		 * en orden inverso. */
		while (nodoActual.getPadre() != null) {
			listaAcciones.add(nodoActual.getAccionGeneradora());
			nodoActual = nodoActual.getPadre();
		}
		
		/* Si la lista de acciones es vacía, entonces ninguna acción fue necesaria, y el
		 * nodo ya se encuenta en un estado objetivo. */
		if (listaAcciones.isEmpty())
			listaAcciones.add(NoAccion.getInstancia());

		return listaAcciones;
	}
	
	private Collection<Nodo> expandir(Nodo unNodo) {
		ArrayList<Nodo> nodosExpandir = new ArrayList<Nodo>();
		
		/* Expando el nodo unNodo sólo si se cumple la precondición de cada
		 * acción. */
		for(Accion a : Accion.getAcciones()) {
			if (a.aplicable(unNodo.getEstado()))
				nodosExpandir.add(new Nodo(this, unNodo, a,
						this.estado.getPromedioVarEnergia(a)));
		}
		
		return nodosExpandir;
	}
}

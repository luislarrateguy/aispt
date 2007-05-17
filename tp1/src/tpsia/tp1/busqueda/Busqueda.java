package tpsia.tp1.busqueda;

import java.util.ArrayList;
import java.util.Collection;
import java.util.PriorityQueue;

import org.apache.log4j.Logger;

import tpsia.tp1.Logging;
import tpsia.tp1.acciones.Accion;
import tpsia.tp1.acciones.NoAccion;
import tpsia.tp1.agente.Agente;
import tpsia.tp1.agente.Estado;
import tpsia.tp1.agente.IObjetivo;
import tpsia.tp1.agente.VisionAmbiente;

public abstract class Busqueda {
	protected Estado estado;
	protected IObjetivo objetivo;
	private static int VECES_EJECUTADA = 0;
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
		VECES_EJECUTADA++;
		Nodo.resetID();
		
		Logger log = Logger.getLogger("Busqueda" + ".ejecucion" + Integer.toString(VECES_EJECUTADA));
		log.debug("Buscar accion");
		log.debug(log.getName());
		
		ArrayList<Accion> listaAcciones = new ArrayList<Accion>();
		PriorityQueue<Nodo> colaNodos = new PriorityQueue<Nodo>();
		ArrayList<VisionAmbiente> estadosAlcanzados = new ArrayList<VisionAmbiente>();
		
		Nodo nodoActual = new Nodo((Estado)this.estado.clone());
		log.debug("Comenzando busqueda. Nodo actual:");
		log.debug(nodoActual);
		/*
		 * Mientras no se cumple el objetivo en el nodo actual, seguimos expandiendo.
		 */
		log.debug("Expandiendo nodo Inicial: " + nodoActual.getID());
		while ( ! this.objetivo.cumpleObjetivo(nodoActual) ) {
			
			log.debug(nodoActual);
			
			/* Vemos si el nodo actual ya fue inspeccionado. */
			
			if (!estadosAlcanzados.contains(nodoActual.getEstado().getAmbiente())) {
				
				/* Agrego el nodo actual a la lista de nodos ya inspeccionados. */
				estadosAlcanzados.add(nodoActual.getEstado().getAmbiente());
				
				for (Nodo n : this.expandir(nodoActual)) {
					colaNodos.add(n);
				}
			}
			
			if (!colaNodos.isEmpty()) {
				nodoActual = colaNodos.remove();
				log.debug("Nodo a expandir: " + nodoActual.getID()  + " hijo de "+ nodoActual.getPadre().getID());
			} else {
				break;
			}
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

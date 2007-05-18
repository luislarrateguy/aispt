package tpsia.tp1.busqueda;

import java.util.ArrayList;
import java.util.Collection;
import java.util.PriorityQueue;

import org.apache.log4j.Logger;

import tpsia.tp1.acciones.Accion;
import tpsia.tp1.acciones.NoAccion;
import tpsia.tp1.agente.Estado;
import tpsia.tp1.agente.IObjetivo;
import tpsia.tp1.agente.VisionAmbiente;

public abstract class Busqueda {
	protected Estado estado;
	protected IObjetivo objetivo;
	protected ArrayList<VisionAmbiente> estadosAlcanzadosAgente;
	
	private static int VECES_EJECUTADA = 0;
	/**
	 * La función de evaluación representa, en:
	 * - Estrategia de amplitud: costo (nodo padre + 1)
	 * - Estrategia de costo uniforme: costo (nodo padre + costo acción)
	 * - Estrategia A*: costo (costouniforme + heurística)
	 * - etc...
	 * @param padre
	 * @param accionGeneradora
	 * @return
	 */
	protected abstract float calcularPrioridad(Nodo unNodo);
	
	public Busqueda(Estado estado, IObjetivo objetivo, ArrayList<VisionAmbiente> estadosAlcanzadosAgente) {
		this.estado = estado;
		this.objetivo = objetivo;
		this.estadosAlcanzadosAgente = estadosAlcanzadosAgente;
	}
	
	public ArrayList<Accion> buscarSolucion() {
		VECES_EJECUTADA++;
		Nodo.resetID();
		Logger log = Logger.getLogger("Pacman.Busqueda" + ".ejecucion" + Integer.toString(VECES_EJECUTADA));
		log.info("Buscar accion");
		
		ArrayList<Accion> listaAcciones = new ArrayList<Accion>();
		PriorityQueue<Nodo> colaNodos = new PriorityQueue<Nodo>();
		
		/* Creo un ArrayList de estados ya alcanzados. El agente almacena estados
		 * ya alcanzados también, pero esta es una copia local para realizar la búsqueda.
		 * Sin embargo, antes de continuar con la búsqueda, copio a estadosAlcanzados
		 * (la versión local) los estados a los que ya no queremos llegar desde la
		 * versión del agente. De esta forma evitamos secuencias de acciones como:
		 * izq, izq, der, der.
		 */
		ArrayList<VisionAmbiente> estadosAlcanzados = (ArrayList<VisionAmbiente>)this.estadosAlcanzadosAgente.clone();
		
		Nodo nodoActual = new Nodo((Estado)this.estado.clone());
		log.debug("Estoy buscando. Nodo actual:");
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
				
				colaNodos.remove(nodoActual);
			}
			
			if (!colaNodos.isEmpty()) {
				nodoActual = colaNodos.remove();
				log.debug("Nodo a expandir: " + nodoActual.getID()  + " hijo de "+ nodoActual.getPadre().getID());
			} else {
				break;
			}
		}
		
		/* nodoActual es un nodo que cumple con el objetivo. Entonces agregamos
		 * a la lista de acciones a devolver las acciones que fueron necesarias
		 * para llegar a este nodo objetivo. Notar que la lista de acciones está
		 * en orden inverso. */
		while (nodoActual.getPadre() != null) {
			listaAcciones.add(nodoActual.getAccionGeneradora());
			nodoActual = nodoActual.getPadre();
		}
		log.info("Llegaré a mi objetivo si ejecuto esta secuencia de acciones");
		log.info("ultima->primera"+listaAcciones);
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

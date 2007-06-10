/*

 Copyright (c) 2007 by Luis I. Larrateguy y Milton Pividori
 All Rights Reserved

 This library is free software; you can redistribute it and/or
 modify it under the terms of the GNU Lesser General Public
 License as published by the Free Software Foundation; either
 version 2.1 of the License, or (at your option) any later version.

 This library is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 Lesser General Public License for more details.

 You should have received a copy of the GNU Lesser General Public
 License along with this library; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

 */
package tpsia.tp1.busqueda;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.PriorityQueue;

import org.apache.log4j.Logger;

import tpsia.tp1.acciones.Accion;
import tpsia.tp1.agente.Estado;
import tpsia.tp1.agente.IObjetivo;
import tpsia.tp1.agente.VisionAmbiente;

public abstract class Busqueda {
	protected Estado estado;
	protected IObjetivo objetivo;
	protected ArrayList<VisionAmbiente> estadosAlcanzadosAgente;
	
	/** Sólo usada para debugging */
	private static int VECES_EJECUTADA = 0;
	public static Logger logxml;
	public static Logger logLatex;
	
	/**
	 * La función de evaluación representa, en:
	 * - Estrategia de amplitud: costo (nodo padre + 1)
	 * - Estrategia de costo uniforme: costo (nodo padre + costo acción)
	 * - Estrategia A*: costo (costouniforme + heurística)
	 * - etc... (ver cada clase)
	 * @param padre
	 * @param accionGeneradora
	 * @return
	 */
	protected abstract float calcularPrioridad(Nodo unNodo);
	protected abstract String nombreEstrategia();
	
	public Busqueda(Estado estado, IObjetivo objetivo, ArrayList<VisionAmbiente> estados) {
		this.estado = estado;
		this.objetivo = objetivo;
		this.estadosAlcanzadosAgente = estados;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Accion> buscarSolucion() {
		VECES_EJECUTADA++;
		/* Salida jerárquica del árbol de búsqueda */
		logxml =  Logger.getLogger("Pacman.Busqueda.ejecucion" + Integer.toString(VECES_EJECUTADA)+".xml");
		logLatex = Logger.getLogger("Pacman.Busqueda.ejecucion" + Integer.toString(VECES_EJECUTADA) + ".tex");
		Logger log = Logger.getLogger("Pacman.Busqueda" + ".ejecucion" + Integer.toString(VECES_EJECUTADA));
		log.info("Buscar accion");
		
		/* Unicamente utilizada para mostrar la salida en latex (debugger) */
		LinkedList<Nodo> nodosSeleccionados;
		
		Nodo.resetID();
		
		Nodo nodoActual;
		Nodo raiz;
		ArrayList<Accion> listaAcciones;
		PriorityQueue<Nodo> colaNodos;
		ArrayList<VisionAmbiente> estadosAlcanzados;
		
		/* Unicamente utilizada para mostrar la salida en latex (debugger) */
		nodosSeleccionados = new LinkedList<Nodo>();
		listaAcciones = new ArrayList<Accion>();
		colaNodos = new PriorityQueue<Nodo>();
		estadosAlcanzados = new ArrayList<VisionAmbiente>();
		raiz = new Nodo((Estado)this.estado.clone());
		nodoActual = raiz;
		nodosSeleccionados.add(raiz);
		
		/* Creo un ArrayList de estados ya alcanzados. El agente almacena estados
		 * ya alcanzados también, pero esta es una copia local para realizar la búsqueda.
		 * Sin embargo, antes de continuar con la búsqueda, copio a estadosAlcanzados
		 * (la versión local) los estados a los que ya no queremos llegar desde la
		 * versión del agente. De esta forma evitamos secuencias de acciones como:
		 * izq, izq, der, der.
		 * No las evita..
		 * Igual esas secuencias de acciones no deberían ocurrir debido a la heurística 
		 * y costo! no deberían!
		 */
		log.debug("Estoy buscando. Nodo actual:");
		estadosAlcanzados.addAll((ArrayList<VisionAmbiente>) this.estadosAlcanzadosAgente);
		/*
		 * Mientras no se cumple el objetivo en el nodo actual, seguimos expandiendo.
		 */
		log.debug("Expandiendo nodo Inicial: " + nodoActual.getID());
		boolean estadoAlcanzado;
		Collection<Nodo> nodosExpandir;
		
		while ( ! this.objetivo.cumpleObjetivo(nodoActual) ) {
			log.debug(nodoActual);
			log.debug(nodoActual.getEstado().getAmbiente());
			log.debug("Prioridad: "+nodoActual.getPrioridadExpansion());
			
			nodosExpandir = this.expandir(nodoActual);
			/* Que no tenga ningun nodo a expandir significa que no era un nodo
			 * objetivo (porque ingreso al while) y que además llevó a la muerte
			 * al pacman. Así que lo saco de los estados repetidos para
			 * decrementar esa lista.
			 */
			if (!nodosExpandir.isEmpty()) {
				for (Nodo n : nodosExpandir) {
					/* Vemos si el nodo expandido ya fue inspeccionado. */
						estadoAlcanzado = estadosAlcanzados.contains(n.getEstado().getAmbiente());
						if (!estadoAlcanzado) {
							/* Agrego el nodo expandido a la lista de nodos ya inspeccionados. */
							estadosAlcanzados.add(n.getEstado().getAmbiente());
							colaNodos.add(n);
						} else {
							log.debug("Estado repetido de busqueda:"+n.getID() + " accion generadora: " +
									n.getAccionGeneradora() + " hijo de: " + n.getPadre().getID());
						}
				}
			} else {
				estadosAlcanzados.remove(nodoActual.getEstado().getAmbiente());
			}
			
			if (!colaNodos.isEmpty()) {
				nodoActual = colaNodos.remove();
				if (logLatex.isDebugEnabled()) {
					nodosSeleccionados.add(nodoActual);
				}
				log.debug("Nodo a expandir: " + nodoActual.getID()  + " hijo de "+ nodoActual.getPadre().getID());
				log.debug(nodoActual.getEstado().getAmbiente());
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
		log.info("Cantidad de nodos generados: " + Nodo.getLastId());
			
		/* Mejora para la velocidad de ejecución si no corresponde debugging */
		if (logxml.isDebugEnabled()) {
			logxml.debug("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
			raiz.toXML();
		}
		if (logLatex.isDebugEnabled()) {
			this.toDocumentLatex(nodosSeleccionados, 16);
		}
			
		return listaAcciones;
	}
	
	private Collection<Nodo> expandir(Nodo unNodo) {
		ArrayList<Nodo> nodosExpandir = new ArrayList<Nodo>();
		Logger log = Logger.getLogger("Pacman.Busqueda" + ".ejecucion" 
				+ Integer.toString(VECES_EJECUTADA));
		
		/* Expando el nodo unNodo sólo si se cumple la precondición de cada
		 * acción. */
		for(Accion a : Accion.getAcciones()) {
			if (a.aplicable(unNodo.getEstado())) {
				log.debug("Aplicable: " + a);
				nodosExpandir.add(new Nodo(this, unNodo, a,
						this.estado.getPromedioVarEnergia(a)));
			}
		}
		
		return nodosExpandir;
	}
	
	private void toDocumentLatex(LinkedList<Nodo> nodosSeleccionados, int niveles) {
		
		/* Salida para latex (genera un arbol usando el paquete qtree). El 
		 * siguiente código genera el documento completo para compilar. Sólo 
		 * hay que disponer de los paquetes necesarios. Debido a la forma en 
		 * que dibuja, no es muy útil si se incluyen muchos niveles (ver método 
		 * toLatex de la clase Nodo). */
		
		// Clase del documento y opciones generales
		Busqueda.logLatex.debug("\\documentclass[a0,landscale]{a0poster}");
		
		// Paquetes utilizados
		Busqueda.logLatex.debug("\\usepackage{mathptmx}");
		Busqueda.logLatex.debug("\\usepackage[scaled=.90]{helvet}");
		Busqueda.logLatex.debug("\\usepackage{courier}");
		Busqueda.logLatex.debug("\\usepackage{qtree}");
		Busqueda.logLatex.debug("\\usepackage{nodo}");
		Busqueda.logLatex.debug("\\usepackage[spanish]{babel}");
		Busqueda.logLatex.debug("\\usepackage[utf8]{inputenc}");
		
		Busqueda.logLatex.debug("\\title{Árbol de ejecución - Estrategia: " + this.nombreEstrategia() + "}");
		Busqueda.logLatex.debug("\\author{}");
		Busqueda.logLatex.debug("\\begin{document}");
		Busqueda.logLatex.debug("\\maketitle");
		
		StringBuffer sf = new StringBuffer();
		int cuentaArboles = 0;
		int nivelesProcesados = 0;
		
		for (Nodo unNodo : nodosSeleccionados) {
			if (cuentaArboles == 0)
				sf.append("\\begin{figure}[h]\n");
			
			sf.append("\\Tree " + unNodo.toQtree() + "\n");
			cuentaArboles++;

			if (cuentaArboles == 4) {
				cuentaArboles = 0;
				sf.append("\\end{figure}\n");
			}
			
			nivelesProcesados++;
			
			if (nivelesProcesados >= niveles)
				break;
		}
		
		if (cuentaArboles > 0)
			sf.append("\\end{figure}");
		sf.append("\n");
		Busqueda.logLatex.debug(sf.toString());		
		Busqueda.logLatex.debug("\\end{document}");
	}
}

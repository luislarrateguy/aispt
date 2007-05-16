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
import java.util.PriorityQueue;

import tpsia.tp1.Logging;
import tpsia.tp1.acciones.*;
import tpsia.tp1.agente.Estado;
import tpsia.tp1.agente.IObjetivo;
import tpsia.tp1.agente.VisionAmbiente;

public class BusquedaCostoUniforme extends Busqueda {
	
	static int busquedaNro = 0;
	
	public BusquedaCostoUniforme(Estado estado, IObjetivo objetivo) {
		super(estado, objetivo);
	}

	//@SuppressWarnings("unchecked")
	public ArrayList<Accion> buscarSolucion() {
		Logging.logDebug("PACMAN: buscar accion");
		
		
		ArrayList<Accion> l = new ArrayList<Accion>();
		PriorityQueue<Nodo> colaNodos = new PriorityQueue<Nodo>();
		ArrayList<VisionAmbiente> estadosAlcanzados = new ArrayList<VisionAmbiente>();
		
		Nodo nodoInicial = new Nodo((Estado)this.estado.clone());
		colaNodos.add(nodoInicial);
		
		Nodo nodoActual;
		nodoActual = colaNodos.remove();
		
		/* 
		 * Saqué el while (true) y saqué el break. Me parecía que no es bueno tener un if
		 * dentro de un while siendo que es una estructura de control
		 * pensada para ejecutarse mientras se cumpla una condición
		 */
		while ( ! this.objetivo.cumpleObjetivo(nodoActual) ) {
			if (!estadosAlcanzados.contains(nodoActual.getEstado().getAmbiente())) {
				estadosAlcanzados.add(nodoActual.getEstado().getAmbiente());
				
				/* Puede que el método addAll no inserte los nodos ordenados.
				 * Si no es así, utilizar el bucle for-each que está comentado
				 * abajo. Estaría bueno que haya un método insert
				 */
				// TODO: borrar comentarios
				/* Por lo que dice JavaDoc agrega en el orden que devuelve
				 * el iterador por defecto, por lo tanto como en el foreach
				 * usas el iterador en forma implícita, es lo mismo.
				 * Voy a dejar el iterador, con el for, ya que nos va a servir
				 * para hacer un mejor debugging. Te parece? 
				 */
				//colaNodos.addAll(this.expandir(nodoActual));
				
				for (Nodo n : this.expandir(nodoActual)) {
					colaNodos.add(n);
				}
			}
			
			nodoActual = colaNodos.remove();
		}
		
		/* FIXME: Hubo un problema cuando lo ejecuté una vez a en este punto.
		 * Devolvío NoAccion.
		 */
		while (nodoActual.getPadre() != null) {
			l.add(nodoActual.getAccionGeneradora());
			nodoActual = nodoActual.getPadre();
		}
		
		if (l.isEmpty())
			l.add(NoAccion.getInstancia());

		return l;
	}

	private Collection<Nodo> expandir(Nodo unNodo) {
		ArrayList<Nodo> nodosExpandir = new ArrayList<Nodo>();
		
		/* Expando el nodo unNodo sólo si se cumple la precondición de cada
		 * acción. */
		for(Accion a : Accion.getAcciones()) {
			if (a.aplicable(unNodo.getEstado().getAmbiente()))
				nodosExpandir.add(new Nodo(unNodo, a,
						this.estado.getPromedioVarEnergia(a)));
		}
		//TODO: borrar lel código comentado debajo.
		/* 
		// AvanzarAbajo
		Accion accion;
		
		accion = AvanzarAbajo.getInstancia();
		if (accion.aplicable(unNodo.getEstado().getAmbiente()))
			nodosExpandir.add(new Nodo(unNodo, accion,
					-this.estado.getPromedioPerdida(accion)));

		// AvanzarArriba
		accion = AvanzarArriba.getInstancia();
		if (accion.aplicable(unNodo.getEstado().getAmbiente()))
			nodosExpandir.add(new Nodo(unNodo, accion,
					-this.estado.getPromedioPerdida(accion)));
		
		// AvanzarDerecha
		accion = AvanzarDerecha.getInstancia();
		if (accion.aplicable(unNodo.getEstado().getAmbiente()))
			nodosExpandir.add(new Nodo(unNodo, accion,
					-this.estado.getPromedioPerdida(accion)));
		
		// AvanzarIzquierda
		accion = AvanzarIzquierda.getInstancia();
		if (accion.aplicable(unNodo.getEstado().getAmbiente()))
			nodosExpandir.add(new Nodo(unNodo, accion,
					-this.estado.getPromedioPerdida(accion)));
		
		// Comer
		accion = Comer.getInstancia();
		if (accion.aplicable(unNodo.getEstado().getAmbiente()))
			nodosExpandir.add(new Nodo(unNodo, accion,
					-this.estado.getPromedioPerdida(accion)));;
		
		// Pelear
		accion = Pelear.getInstancia();
		if (accion.aplicable(unNodo.getEstado().getAmbiente()))
			nodosExpandir.add(new Nodo(unNodo, accion,
					-this.estado.getPromedioPerdida(accion)));
		*/
		return nodosExpandir;
	}

}

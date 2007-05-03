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
import tpsia.tp1.acciones.AvanzarAbajo;
import tpsia.tp1.acciones.AvanzarArriba;
import tpsia.tp1.acciones.AvanzarDerecha;
import tpsia.tp1.acciones.AvanzarIzquierda;
import tpsia.tp1.acciones.Comer;
import tpsia.tp1.acciones.IAccion;
import tpsia.tp1.acciones.NoAccion;
import tpsia.tp1.acciones.Pelear;
import tpsia.tp1.agente.Estado;
import tpsia.tp1.agente.IObjetivo;
import tpsia.tp1.agente.VisionAmbiente;

public class BusquedaCostoUniforme extends Busqueda {
	public BusquedaCostoUniforme(Estado estado, IObjetivo objetivo) {
		super(estado, objetivo);
	}

	//@SuppressWarnings("unchecked")
	public ArrayList<IAccion> buscarSolucion() {
		// TODO buscar secuencia de acciones
		Logging.logDebug("PACMAN: buscar accion");
		ArrayList<IAccion> l = new ArrayList<IAccion>();
		PriorityQueue<Nodo> colaNodos = new PriorityQueue<Nodo>();
		ArrayList<VisionAmbiente> estadosAlcanzados = new ArrayList<VisionAmbiente>();
		
		Nodo nodoInicial = new Nodo((Estado)this.estado.clone());
		colaNodos.add(nodoInicial);
		
		Nodo nodoActual;
		
		while (true) {
			nodoActual = colaNodos.remove();
			
			if (this.objetivo.cumpleObjetivo(nodoActual))
				break;
			
			if (!estadosAlcanzados.contains(nodoActual.getEstado().getAmbiente())) {
				estadosAlcanzados.add(nodoActual.getEstado().getAmbiente());
				
				/* Puede que el método addAll no inserte los nodos ordenados.
				 * Si no es así, utilizar el bucle for-each que está comentado
				 * abajo. Estaría bueno que halla un método insert*/
				colaNodos.addAll(this.expandir(nodoActual));
				/*
				for (Nodo n : this.expandir(unNodo))
					colaNodos.add(n);
				*/
			}
		}
		
		while (nodoActual.getPadre() != null) {
			l.add(nodoActual.getAccionGeneradora());
			nodoActual = nodoActual.getPadre();
		}
		
		if (l.isEmpty())
			l.add(NoAccion.getInstancia());
		
		return l;
	}

	private Collection<Nodo> expandir(Nodo unNodo) {
		ArrayList<Nodo> nodosExpandidos = new ArrayList<Nodo>();
		
		/* Expando el nodo unNodo sólo si se cumple la precondición de cada
		 * acción. */
		
		// AvanzarAbajo
		IAccion avanzarAbajo = AvanzarAbajo.getInstancia();
		if (avanzarAbajo.aplicable(unNodo.getEstado().getAmbiente()))
			nodosExpandidos.add(new Nodo(unNodo, avanzarAbajo,
					-this.estado.getPromEnergiaPerdidaAvanzar()));
		
		// AvanzarArriba
		IAccion avanzarArriba = AvanzarArriba.getInstancia();
		if (avanzarArriba.aplicable(unNodo.getEstado().getAmbiente()))
			nodosExpandidos.add(new Nodo(unNodo, avanzarArriba,
					-this.estado.getPromEnergiaPerdidaAvanzar()));
		
		// AvanzarDerecha
		IAccion avanzarDerecha = AvanzarDerecha.getInstancia();
		if (avanzarDerecha.aplicable(unNodo.getEstado().getAmbiente()))
			nodosExpandidos.add(new Nodo(unNodo, avanzarDerecha,
					-this.estado.getPromEnergiaPerdidaAvanzar()));
		
		// AvanzarIzquierda
		IAccion avanzarIzquierda = AvanzarIzquierda.getInstancia();
		if (avanzarIzquierda.aplicable(unNodo.getEstado().getAmbiente()))
			nodosExpandidos.add(new Nodo(unNodo, avanzarIzquierda,
					-this.estado.getPromEnergiaPerdidaAvanzar()));
		
		// Comer
		IAccion comer = Comer.getInstancia();
		if (comer.aplicable(unNodo.getEstado().getAmbiente()))
			nodosExpandidos.add(new Nodo(unNodo, comer,
					-this.estado.getPromedioEnergiaPerdidaComer()));
		
		// Pelear
		IAccion pelear = Pelear.getInstancia();
		if (pelear.aplicable(unNodo.getEstado().getAmbiente()))
			nodosExpandidos.add(new Nodo(unNodo, pelear,
					-this.estado.getPromedioEnergiaPerdidaPelear()));
		
		return nodosExpandidos;
	}

}

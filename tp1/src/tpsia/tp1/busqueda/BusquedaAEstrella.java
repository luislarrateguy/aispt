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

import tpsia.tp1.acciones.Comer;
import tpsia.tp1.acciones.Pelear;
import tpsia.tp1.agente.Estado;
import tpsia.tp1.agente.IObjetivo;
import tpsia.tp1.agente.VisionAmbiente;

public class BusquedaAEstrella extends Busqueda {

	public BusquedaAEstrella(Estado estado, IObjetivo objetivo, ArrayList<VisionAmbiente> estadosAlcanzadosAgente) {
		super(estado, objetivo, estadosAlcanzadosAgente);
	}
	
	@Override
	protected float calcularPrioridad(Nodo unNodo) {
		/* Calculo el costo */
		float costo = unNodo.getPadre().getPrioridadExpansion() + unNodo.getAccionGeneradora().getCosto();
		
		/* Calculo la función heurística, que consiste en la cantidad de celdas que
		 * son desconocidas. */
		float heuristica1 = unNodo.getEstado().getAmbiente().cantidadCeldasDesconocidas();
		
		/* Otra heurística que se nos ocurrió utilizar pero de la cual teníamos dudas.
		 * Es consistente.
		
		 float heuristica2 = unNodo.getEstado().getAmbiente().cantidadComidaVisible()*3;
		 float heuristica3 = unNodo.getEstado().getAmbiente().cantidadEnemigosVisible()*2;
		 float promedioComer = unNodo.getEstado().getPromedioVarEnergia(Comer.getInstancia());
		 float promedioPelear = unNodo.getEstado().getPromedioVarEnergia(Pelear.getInstancia());
		 int energiaActual = unNodo.getEstado().getEnergia();
		 float heuristica = heuristica1 + heuristica2 + heuristica3;
		 
		/* */
		float heuristica = heuristica1;
		/* Retorno el valor de la función de evaluación = costo + heuristica */
		return (costo + heuristica);
	}

	@Override
	protected String nombreEstrategia() {
		return "A*";
	}
}

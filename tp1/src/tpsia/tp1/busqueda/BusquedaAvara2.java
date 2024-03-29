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

import tpsia.tp1.EstadoCelda;
import tpsia.tp1.agente.Estado;
import tpsia.tp1.agente.IObjetivo;
import tpsia.tp1.agente.VisionAmbiente;

public class BusquedaAvara2 extends Busqueda {

	public BusquedaAvara2(Estado estado, IObjetivo objetivo, ArrayList<VisionAmbiente> estadosAlcanzadosAgente) {
		super(estado, objetivo, estadosAlcanzadosAgente);
	}
	
	@Override
	protected float calcularPrioridad(Nodo unNodo) {
		float heuristica1 = unNodo.getEstado().getAmbiente().cantidadCeldasDesconocidas();
		float heuristica2 = unNodo.getEstado().getAmbiente().cantidadComidaVisible();
		float heuristica3 = unNodo.getEstado().getAmbiente().cantidadEnemigosVisible();
		
		// Heurística rara que estoy probando :)
		float heuristica4 = unNodo.getEstado().getAmbiente().distanciaObjeto(EstadoCelda.Comida)*2;
		float heuristica5 = unNodo.getEstado().getAmbiente().distanciaObjeto(EstadoCelda.Enemigo);
		
		
		float heuristica = heuristica1*h1 + heuristica2*h2 + heuristica3*h3 + heuristica4 + heuristica5;
		
		return heuristica;
	}

	@Override
	protected String nombreEstrategia() {
		return "Avara 2";
	}

}

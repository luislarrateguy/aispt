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
package tpsia.tp1.agente;

import org.apache.log4j.Logger;

import tpsia.tp1.acciones.AvanzarAbajo;
import tpsia.tp1.acciones.Pelear;
import tpsia.tp1.busqueda.Nodo;

public class ObjetivoTP implements IObjetivo {
	private static ObjetivoTP instancia;
	
	public ObjetivoTP() {
		super();
	}
	/**
	 * Patrón Singleton
	 * @return
	 */
	public static ObjetivoTP getInstancia() {
		if (instancia == null)
			instancia = new ObjetivoTP();
		
		return instancia;
	}
	
	public boolean cumpleObjetivo(Estado estado) {
		boolean cumplio = false;
		boolean convienePelear,convieneMoverse,conoceTodo,hayAlimentos,hayEnemigos;
		
		Logger log = Logger.getLogger("Pacman.Busqueda.Objetivo");
		
		if (estado.getEnergia() <= 0) {
			cumplio = false; /* Si el pacman muere en el nodo, no cumple el objetivo */
		} else {
			
			convienePelear = (estado.getEnergia() +	estado.getPromedioVarEnergia(Pelear.getInstancia())) > 0;
			convieneMoverse = (estado.getEnergia() + estado.getPromedioVarEnergia(AvanzarAbajo.getInstancia())) > 0;			
				
			conoceTodo = estado.getAmbiente().conoceTodo();
			hayAlimentos = estado.getAmbiente().hayAlimentosSinComer();
			hayEnemigos = estado.getAmbiente().hayEnemigosVivos();

			cumplio = (conoceTodo || !convieneMoverse) && (!hayAlimentos && (!hayEnemigos || !convienePelear));

			if (cumplio) {
				log.debug("Conviene pelear: "+convienePelear + " Conviene moverse: "+convieneMoverse +
						" Conoce todo: "+conoceTodo + " Hay alimentos: "+hayAlimentos + " Hay enemigos: "+hayEnemigos);
			}
		}
		
		return cumplio;
	}

	public boolean cumpleObjetivo(Nodo nodo) {
		return this.cumpleObjetivo(nodo.getEstado());
	}

}

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

import tpsia.tp1.busqueda.Nodo;

public class ObjetivoSimple implements IObjetivo {
	private static ObjetivoSimple instancia;
	
	public ObjetivoSimple() {
		super();
	}
	
	public static ObjetivoSimple getInstancia() {
		if (instancia == null)
			instancia = new ObjetivoSimple();
		
		return instancia;
	}
	
	public boolean cumpleObjetivo(Estado estado) {
		boolean cumplio = false;
		boolean conoceTodo,hayAlimentos,hayEnemigos;
		
		Logger log = Logger.getLogger("Pacman.Busqueda.Objetivo");	
		if (estado.getEnergia() <= 0) {
			cumplio = false;
		} else {		
			conoceTodo = estado.getAmbiente().conoceTodo();
			hayAlimentos = estado.getAmbiente().hayAlimentosSinComer();
			hayEnemigos = estado.getAmbiente().hayEnemigosVivos();

			cumplio =  conoceTodo && !hayAlimentos && !hayEnemigos;

			if (cumplio) {
				log.debug("Conoce todo: "+conoceTodo + " Hay alimentos: "+hayAlimentos + 
						" Hay enemigos: "+hayEnemigos);
			}
		}
		
		return cumplio;
	}

	public boolean cumpleObjetivo(Nodo nodo) {
		return this.cumpleObjetivo(nodo.getEstado());
	}

}

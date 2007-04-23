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

import java.util.ArrayList;
import java.util.Vector;

import calculador.Pair;

import tpsia.tp1.Logging;
import tpsia.tp1.Percepcion;
import tpsia.tp1.acciones.IAccion;
import tpsia.tp1.busqueda.BusquedaCostoUniforme;
import tpsia.tp1.busqueda.IBusqueda;

public class Agente {

	private Estado est;
	private IBusqueda bus;
	private ArrayList<IAccion> acciones;
	private Objetivo o;
	
	public Agente() {
		this.est = new Estado();
		this.bus = new BusquedaCostoUniforme();
	}

	public IAccion actuar(Percepcion p) {
		this.actualizarEstado(p);
		this.acciones = bus.buscarSolucion(this.est);
		IAccion a = this.acciones.get(0);
		a.ejecutar(this.est);
		/* TODO A esta altura del código podríamos guardar cosas 
		 * como	última acción ejecutada y demás. */
		return a;
	}

	private void actualizarEstado(Percepcion p2) {
		// TODO actualizar visión del ambiente con la nueva percepcion
		Logging.logDebug("PACMAN: actualizar visión del ambiente con la nueva percepcion");
	}

	private void formularObjetivo() {
		// TODO formular objetivo
		Logging.logDebug("PACMAN: formular objetivo");
		this.o = new Objetivo(this.est);
	}

	public void inicializar(Vector pene, Vector pcom, Pair ppac) {
		//est.getVAmbiente().
		
	}
}
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

import tpsia.tp1.Logging;
import tpsia.tp1.Percepcion;
import tpsia.tp1.acciones.IAccion;
import tpsia.tp1.busqueda.Busqueda;
import tpsia.tp1.busqueda.BusquedaCostoUniforme;

public class Agente {

	private Estado estado;
	private IObjetivo objetivo;
	private Busqueda busqueda;
	private ArrayList<IAccion> acciones;
	
	public Agente(int energiaInicial) {
		this.estado = new Estado(energiaInicial);
		this.objetivo = ObjetivoTP.getInstancia();
		this.busqueda = new BusquedaCostoUniforme(this.estado, this.objetivo);
	}

	public IAccion actuar(Percepcion p) {
		Logging.logDebug("AGENTE: Percepción recibida. Actuando...");
		this.estado.actualizarEstado(p);
		
		Logging.logMensaje(this.estado.getAmbiente().draw());
		Logging.logMensaje("energia:" 
			+ Integer.toString(this.estado.getEnergia()) + "\n");
		
		this.acciones = busqueda.buscarSolucion();
		IAccion a = this.acciones.get(this.acciones.size() - 1);
		
		Logging.logDebug("AGENTE: Se decidió la acción: " + a.getTipoAccion());
		this.estado.ejecutarAccion(a);
		//a.ejecutar(this.estado.getAmbiente());
		
		/* TODO A esta altura del código podríamos guardar cosas 
		 * como	última acción ejecutada y demás. */
		return a;
	}
	
	public boolean cumplioObjetivo() {
		return this.objetivo.cumpleObjetivo(this.estado);
	}
	
	public boolean vivo() {
		return (this.estado.getEnergia() > 0);
	}
}
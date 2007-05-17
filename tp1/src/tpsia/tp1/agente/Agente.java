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

import org.apache.log4j.*;
import tpsia.tp1.Logging;
import tpsia.tp1.Percepcion;
import tpsia.tp1.acciones.Accion;
import tpsia.tp1.acciones.NoAccion;
import tpsia.tp1.busqueda.*;

public class Agente {

	private Estado estado;
	private IObjetivo objetivo;
	private Busqueda busqueda;
	private ArrayList<Accion> acciones;
	
	public Agente(int energiaInicial) {
		this.estado = new Estado(energiaInicial);
		this.objetivo = ObjetivoTP.getInstancia();
		// Selecciona y CTRL+SHIFT+C
//		this.busqueda = new BusquedaAmplitud(this.estado, this.objetivo);
//		this.busqueda = new BusquedaCostoUniforme (this.estado, this.objetivo);
//		this.busqueda = new BusquedaAvara(this.estado, this.objetivo);
		this.busqueda = new BusquedaAEstrella(this.estado, this.objetivo);
	}

	public Accion actuar(Percepcion p) {
		Logger log = Logger.getLogger(Agente.class);
		log.debug("Percepción recibida. Actuando...");
		this.estado.actualizarEstado(p);
		
		Logging.logMensaje(this.estado.getAmbiente().draw());
		Logging.logMensaje("energia:" 
			+ Integer.toString(this.estado.getEnergia()) + "\n");
		
		this.acciones = busqueda.buscarSolucion();
		Accion a = this.acciones.get(this.acciones.size() - 1);
		
		log.debug("Se decidió la acción: " + a.getTipoAccion());
		if (!a.getClass().equals(NoAccion.class))
			this.estado.ejecutarAccion(a);
		return a;
	}
	
	public boolean cumplioObjetivo() {
		return this.objetivo.cumpleObjetivo(this.estado);
	}
	
	public boolean vivo() {
		return (this.estado.getEnergia() > 0);
	}
	public void mostrarEstadoFinal() {
		Logger log = Logger.getLogger(Agente.class);
		log.info("ESTADO FINAL");	
		log.info(this.estado.getAmbiente());
		log.info("energia:" 
			+ Integer.toString(this.estado.getEnergia()) + "\n");
	}
}
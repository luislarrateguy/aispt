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
	private ArrayList<VisionAmbiente> estadosAlcanzados;
	private boolean cumplioObjetivo;
	
	public Agente(int energiaInicial) {
		super();
		this.cumplioObjetivo = false;
		this.estado = new Estado(energiaInicial);
//		this.objetivo = ObjetivoTP.getInstancia();
		this.objetivo = ObjetivoSimple.getInstancia();
		this.estadosAlcanzados = new ArrayList<VisionAmbiente>();
		// Selecciona y CTRL+SHIFT+C
//		this.busqueda = new BusquedaAmplitud(this.estado, this.objetivo, estadosAlcanzados);
		this.busqueda = new BusquedaCostoUniforme (this.estado, this.objetivo, this.estadosAlcanzados);
//		this.busqueda = new BusquedaAvara(this.estado, this.objetivo, estadosAlcanzados);
//		this.busqueda = new BusquedaAEstrella(this.estado, this.objetivo, estadosAlcanzados);
	}

	public Accion actuar(Percepcion p) {
		Logger log = Logger.getLogger(Agente.class);
		log.debug("Percepción recibida. Actuando...");
		this.estado.actualizarEstado(p);

		log.info(this.estado.getAmbiente());
		log.info("energia:" 
			+ Integer.toString(this.estado.getEnergia()) + "");
		
		
		this.acciones = busqueda.buscarSolucion();
		Accion a = this.acciones.get(this.acciones.size() - 1);
		
		log.info("Se decidió la acción: " + a.getTipoAccion());
		if (!a.getClass().equals(NoAccion.class)) {
			this.estado.ejecutarAccion(a);
			this.estadosAlcanzados.add(this.estado.getAmbiente());
		}
		this.cumplioObjetivo = this.objetivo.cumpleObjetivo(this.estado);
		return a;
	}
	
	public boolean cumplioObjetivo() {
		return this.cumplioObjetivo;
	}
	
	public boolean vivo() {
		return (this.estado.getEnergia() > 0);
	}
	public void mostrarEstadoFinal() {
		Logger log = Logger.getLogger(Agente.class);
		log.info("ESTADO FINAL");	
		log.info(this.estado.getAmbiente());
		log.info("energia:" 
			+ Integer.toString(this.estado.getEnergia()));
	}
}
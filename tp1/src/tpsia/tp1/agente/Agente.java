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

import org.apache.log4j.Logger;

import tpsia.tp1.Percepcion;
import tpsia.tp1.acciones.Accion;
import tpsia.tp1.busqueda.Busqueda;
import tpsia.tp1.busqueda.BusquedaFactory;

public class Agente {

	private Estado estado;
	private IObjetivo objetivo;
	private Busqueda busqueda;
	private ArrayList<Accion> acciones;
	private ArrayList<VisionAmbiente> estadosAlcanzados;
	private boolean cumplioObjetivo;
	private boolean tieneSolucion;
	
	/**
	 * Crea un Agente.
	 * Hace uso de BusquedaFactory para crear la búsqueda
	 * y aprovecha la implementación del Patrón Strategy
	 * sobre las Búsquedas para cambiar el comportamiento.
	 * 
	 * @param energiaInicial
	 * @param tipoBusqueda
	 */
	public Agente(int energiaInicial, String tipoBusqueda) {
		super();
		this.tieneSolucion = true;
		this.cumplioObjetivo = false;
		this.estado = new Estado(energiaInicial);
		this.objetivo = ObjetivoTP.getInstancia();
		this.estadosAlcanzados = new ArrayList<VisionAmbiente>();
		this.busqueda = BusquedaFactory.Create(tipoBusqueda,this.estado, 
				this.objetivo, this.estadosAlcanzados);
	}

	public Accion actuar(Percepcion p) {
		Logger log = Logger.getLogger(Agente.class);
		log.debug("Percepción recibida. Actuando...");

		this.estado.actualizarEstado(p);

		log.info(this.estado.getAmbiente());
		log.info("energia:" + Integer.toString(this.estado.getEnergia()));
		
		this.acciones = busqueda.buscarSolucion();
		Accion a = null;

		try {
			a = this.acciones.get(this.acciones.size() - 1);
			log.info("Se decidió la acción: " + a.getTipoAccion());
			this.estado.ejecutarAccion(a);
			this.estadosAlcanzados.add((VisionAmbiente)this.estado.getAmbiente().clone());
			this.cumplioObjetivo = this.objetivo.cumpleObjetivo(this.estado);
		} catch (Exception e) {
			/* Si salto la excepcion debido a que la secuencia de acciones 
			 * está vacía, entonces es porque no se encontró solución.
			 * No se cumple el objetivo en ningún nodo.
			 */
			a = null;
			this.tieneSolucion = false;
			log.warn("No se encontró solución que satisfaga el objetivo");
		}

		return a;
	}
	
	public boolean cumplioObjetivo() {
		return this.cumplioObjetivo || !this.tieneSolucion;
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
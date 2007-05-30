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

package tpsia.tp2.agente;

import org.apache.log4j.Logger;

import tpsia.tp2.Percepcion;
import tpsia.tp2.acciones.Accion;

public class Agente {
	private BaseConocimiento baseConocimiento;
	private int tiempo;
	
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
		
		this.baseConocimiento = new BaseConocimiento();
		this.tiempo = 0;
	}

	public Accion actuar(Percepcion p) {
		Logger log = Logger.getLogger(Agente.class);
		log.debug("Percepción recibida. Actualizando BC...");

		this.baseConocimiento.decir(p);

		// TODO: Ver como traducir esto al tp2
		//log.info(this.estado.getAmbiente());
		//log.info("energia:" + Integer.toString(this.estado.getEnergia()));
		
		Accion a = this.baseConocimiento.preguntar(this.tiempo);
		
		this.baseConocimiento.decir(a);

//		try {
//			a = this.acciones.get(this.acciones.size() - 1);
//			log.info("Se decidió la acción: " + a.getTipoAccion());
//			this.estado.ejecutarAccion(a);
//			this.estadosAlcanzados.add(this.estado.getAmbiente());
//			this.cumplioObjetivo = this.objetivo.cumpleObjetivo(this.estado);
//		} catch (Exception e) {
//			/* Si salto la excepcion debido a que la secuencia de acciones 
//			 * está vacía, entonces es porque no se encontró solución.
//			 * No se cumple el objetivo en ningún nodo.
//			 */
//			a = null;
//			this.tieneSolucion = false;
//			log.fatal("No se encontró solución que satisfaga el objetivo");
//		}

		// Aumento el tiempo
		this.tiempo++;
		
		return a;
	}
	
	public boolean cumplioObjetivo() {
		return this.baseConocimiento.cumplioObjetivo();
	}
	
	public boolean vivo() {
		return this.baseConocimiento.agenteVivo();
	}
	
	public void mostrarEstadoFinal() {
		Logger log = Logger.getLogger(Agente.class);
		log.info("ESTADO FINAL");	
		log.info(this.baseConocimiento.getVisionAmbiente());
		log.info("energia:" 
			+ Integer.toString(this.baseConocimiento.getEnergiaAgente()));
	}
}
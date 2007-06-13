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
import tpsia.tp2.logica.CreadorSentencias;
import tpsia.tp2.logica.sentencias.MejorAccion;

public class Agente {
	private BaseConocimiento baseConocimiento;
	private int tiempo;
	
	/**
	 * TODO: Escribir algo nuevo aca.
	 */
	public Agente() {
		super();
		this.baseConocimiento = new BaseConocimiento();
		this.tiempo = 0;
	}

	public Accion actuar(Percepcion p) {
		Logger log = Logger.getLogger(Agente.class);
		log.debug("Percepción recibida. Actualizando BC...");

		/**
		 * Agrega la percepción a la Base de Conocimiento (KDB)
		 */
		this.baseConocimiento.decir(CreadorSentencias.desdePercepcion(p, this.tiempo));

		/**
		 * TODO: Debe analizar si cumplió el objetivo en la situacion
		 * actual S, con la percepción recién agregada.
		 * Si no es así, continua.
		 */
		
		if (this.baseConocimiento.cumplioObjetivo())
			return null;
		
		/**
		 * TODO: Ver como traducir esto al tp2
		 * log.info(this.estado.getAmbiente());
		 * log.info("energia:" + Integer.toString(this.estado.getEnergia()));
		 */

		/**
		 * Pregunta por la mejor accion para realizar
		 */
		MejorAccion ma = null;
		this.baseConocimiento.preguntar(ma);
		Accion a = ma.getMejorAccion();
		
		/**
		 * El aumento del tiempo debería ir acá según lo que entiendo
		 * ya que la percepción que recibis es resultado de tu accion anterior,
		 * por lo tanto, tu nueva accion, corresponde a la situacion s+1
		 * (de la cual percibirás los efectos luego)
		 */
		this.tiempo++;
		
		/**
		 * Avisa a la base de conocimiento la desición de su accion,
		 * para que la misma calcule y deje grabado el estado sucesor
		 * a la espera de una nueva percepción.
		 */
		this.baseConocimiento.decir(CreadorSentencias.desdeAccion(a, this.tiempo));

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
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

import jpl.JPL;

import org.apache.log4j.Logger;

import tpsia.tp2.Percepcion;
import tpsia.tp2.acciones.Accion;

public class Agente {
	private BaseConocimiento baseConocimiento;
	private boolean noAction;
	
	/**
	 * TODO: Escribir algo nuevo aca.
	 * @throws Exception 
	 */
	public Agente() throws Exception {
		super();
		this.noAction = false;
		this.baseConocimiento = new BaseConocimiento();
	}

	public Accion actuar(Percepcion p) {
		Logger log = Logger.getLogger(Agente.class);
		log.debug("Percepción recibida.");
		Accion a = null;
		
		/**
		 * Agrega la percepción a la Base de Conocimiento (KDB)
		 */
		log.debug("Notificando a la BC sobre la percepción");
		this.baseConocimiento.decir(p);
		log.debug(this.baseConocimiento.drawVisionAmbiente());
		/**
		 * TODO: Debe analizar si cumplió el objetivo en la situacion
		 * actual S, con la percepción recién agregada.
		 * Si no es así, continua.
		 */
		
		if (this.baseConocimiento.cumplioObjetivo()) {
			log.debug("De acuerdo a la percepción recibida, ya se llegó al objetivo");
			a = null;
			this.noAction=true;
		} else {
			
			log.debug("Aún no llegamos al objetivo");
			/**
			 * Pregunta por la mejor accion para realizar
			 */
			log.debug("Preguntando por la mejor acción...");
			a = this.baseConocimiento.preguntarMejorAccion();

			
			/**
			 * Avisa a la base de conocimiento la desición de su accion,
			 * para que la misma calcule y deje grabado el estado sucesor
			 * a la espera de una nueva percepción.
			 */
			log.debug("Notificando a la BC sobre la acción elegida");
			if (a != null) {
				log.debug("La mejor acción es: " + a.getTipoAccion());
				this.baseConocimiento.decir(a);
			} else {
				this.noAction=true;
			}
		
		}

		return a;
	}
	
	public boolean cumplioObjetivo() {
		return  this.noAction || this.baseConocimiento.cumplioObjetivo();
	}
	
	public void mostrarEstadoFinal() {
		Logger log = Logger.getLogger(Agente.class);
		log.info("ESTADO FINAL");	
		log.info(this.baseConocimiento.getVisionAmbiente());
		log.info("energia:" 
			+ Integer.toString(this.baseConocimiento.getEnergiaAgente()));
	}
}
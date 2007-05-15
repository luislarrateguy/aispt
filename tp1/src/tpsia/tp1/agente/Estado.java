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

import java.util.Hashtable;
import tpsia.tp1.Percepcion;
import tpsia.tp1.acciones.*;

public class Estado implements Cloneable {

	private int energia;
	private Class ultimaAccionEjecutada;
	
	/**
	 * Un promedio positivo indica que en promedio se esta ganando
	 * energía. Uno negativo, que en promedio se esta perdiendo.
	 */
	private Hashtable<Class, Float> promedios;
	private Hashtable<Class, Integer> vecesEjecutada;
	private VisionAmbiente visionAmbiente;
	public Estado() {
		this(0);
	}
	public Estado(int energiaInicial) {
		this.energia = energiaInicial;
		this.ultimaAccionEjecutada = null;
		
		this.promedios = new Hashtable<Class, Float>();
		this.promedios.put(Avanzar.class, (float)0.0);
		this.promedios.put(Comer.class, (float)0.0);
		this.promedios.put(Pelear.class, (float)0.0);

		this.vecesEjecutada = new Hashtable<Class, Integer>();
		this.vecesEjecutada.put(Avanzar.class, 0);
		this.vecesEjecutada.put(Comer.class, 0);
		this.vecesEjecutada.put(Pelear.class, 0);
		
		this.visionAmbiente = new VisionAmbiente();
	}
	public void ejecutarAccion(Accion a) {
		try {
			a.ejecutar(this.visionAmbiente);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (a instanceof Avanzar)
			this.ultimaAccionEjecutada = Avanzar.class;
		else
			this.ultimaAccionEjecutada = a.getClass();
	}
	public String draw() {
		String cuadro = new String("\n");
		cuadro += this.visionAmbiente.draw() + "\n";
		
		cuadro += "energia:" 
			+ Integer.toString(this.energia) 	+"\n";
		cuadro += "promVarEneAvanz:"
				+ Float.toString(this.promedios.get(Avanzar.class)) 	+"\n";
		cuadro += "promVarEneAvanz:" 
				+ Float.toString(this.promedios.get(Comer.class)) +"\n";
		cuadro += "promVarEneLucha:" 
				+ Float.toString(this.promedios.get(Pelear.class)) +"\n";
		return cuadro;
	}
	public void toXML() {
		/*
		Logging.logXMLOpen("estado");
			visionAmbiente.toXML();
			Logging.logXMLOpen("energia");
			Logging.logXML(Integer.toString(energia));
			Logging.logXMLClose("energia");
			Logging.logXMLOpen("promVarEnergiaAvanzar");
			Logging.logXML(Float.toString(this.promedios.get(Avanzar.class)));
			Logging.logXMLClose("promVarEnergiaAvanzar");
			Logging.logXMLOpen("promVarEnergiaComer");
			Logging.logXML(Float.toString(this.promedios.get(Comer.class)));
			Logging.logXMLClose("promVarEnergiaComer");
			Logging.logXMLOpen("promVarEnergiaPelear");
			Logging.logXML(Float.toString(this.promedios.get(Pelear.class)));
			Logging.logXMLClose("promVarEnergiaPelear");
		Logging.logXMLClose("estado");
		*/
	}
	public void actualizarEstado(Percepcion p) {
		this.visionAmbiente.actualizar(p);
		
		// Cálculo de los promedios
		// TODO: Documentar. Esta comparación... trata de detectar la primer ejecución del algoritmo?
		// Que significa null? no entiendo que intenta comparar
		if (this.ultimaAccionEjecutada != null) {
			int diferenciaEnergia = p.getEnergia() - this.energia;
			
			/* Suma es la sumatoria de diferencias de energía. */
			float suma = this.promedios.get(this.ultimaAccionEjecutada)
				* this.vecesEjecutada.get(this.ultimaAccionEjecutada);
			
			// Actualizo la cantidad de veces que se ejecutó la última acción
			int veces = this.vecesEjecutada.get(this.ultimaAccionEjecutada) + 1;
			this.vecesEjecutada.put(this.ultimaAccionEjecutada, veces);
			
			// Ahora sí, recalculo el promedio
			suma += diferenciaEnergia;
			float promedioNuevo = (suma / veces);
			this.promedios.put(this.ultimaAccionEjecutada, promedioNuevo);
			
			this.ultimaAccionEjecutada = null;
		}
		
		// Actualizo la energía
		this.energia = p.getEnergia();
	}
	public VisionAmbiente getAmbiente() {
		return this.visionAmbiente;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Object clone() {
		Estado estadoClon = new Estado();
		
		estadoClon.energia = this.energia;
		estadoClon.promedios = (Hashtable<Class, Float>) this.promedios.clone();
		estadoClon.vecesEjecutada = (Hashtable<Class, Integer>) this.vecesEjecutada.clone();
		estadoClon.visionAmbiente = (VisionAmbiente) this.visionAmbiente.clone();
		
		return estadoClon;
	}
	public int getEnergia() {
		return energia;
	}
	/**
	 * @deprecated
	 * @return
	 */
	public float getPromedioEnergiaPerdidaComer() {
		return this.promedios.get(Comer.class);
	}
	/**
	 * @deprecated
	 * @return
	 */
	public float getPromedioEnergiaPerdidaPelear() {
		return this.promedios.get(Pelear.class);
	}
	/**
	 * @deprecated 
	 * @return
	 */
	public float getPromEnergiaPerdidaAvanzar() {
		return this.promedios.get(Avanzar.class);
	}
	public float getPromedioVarEnergia(Accion a) {
		return this.promedios.get(a.getClase());
	}
	public Class getUltimaAccionEjecutada() {
		return ultimaAccionEjecutada;
	}
}
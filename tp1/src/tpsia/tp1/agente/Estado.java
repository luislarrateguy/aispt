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

	/**
	 * La energía del agente.
	 */
	private int energia;
	
	/**
	 * Última acción ejecutada por el agente. Es utilizada para realizar
	 * cálculos de promedio de cambio de energía por cada acción.
	 */
	private Accion ultimaAccionEjecutada;
	
	/**
	 * Indica el promedio de cambio de energía por cada acción.
	 * Un promedio positivo indica que en promedio se esta ganando
	 * energía. Uno negativo, que en promedio se esta perdiendo.
	 */
	private float[] promedios;
	
	/**
	 * Indica la cantidad de veces que se ejecutó cada acción.
	 */
	private int[] vecesEjecutada;
	
	/**
	 * El ambiente tal y como lo ve el agente.
	 */
	private VisionAmbiente visionAmbiente;
	
	public Estado() {
		this(0);
	}
	
	public Estado(int energiaInicial) {
		this.energia = energiaInicial;
		this.ultimaAccionEjecutada = null;
		
		this.promedios = new float[Accion.getAcciones().size()];
		this.promedios[0] = 0;
		this.promedios[1] = 0;
		this.promedios[2] = 0;

		this.vecesEjecutada = new int[Accion.getAcciones().size()];
		this.vecesEjecutada[0] = 0;
		this.vecesEjecutada[1] = 0;
		this.vecesEjecutada[2] = 0;
		
		this.visionAmbiente = new VisionAmbiente();
	}
	
	public void ejecutarAccion(Accion a) {
		try {
			a.ejecutar(this.visionAmbiente);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.ultimaAccionEjecutada = a;
	}
	
	public String draw() {
		String cuadro = new String("\n");
		cuadro += this.visionAmbiente.draw() + "\n";
		
		cuadro += "energia:" 
			+ Integer.toString(this.energia) 	+"\n";
		cuadro += "promVarEneAvanz:"
				+ Float.toString(this.promedios[AvanzarAbajo.getInstancia().getIdentificador()]) 	+"\n";
		cuadro += "promVarEneAvanz:" 
				+ Float.toString(this.promedios[Comer.getInstancia().getIdentificador()]) +"\n";
		cuadro += "promVarEneLucha:" 
				+ Float.toString(this.promedios[Pelear.getInstancia().getIdentificador()]) +"\n";
		return cuadro;
	}
	
	public String toXML() {
		String cuadro = new String("<estado>");
		cuadro += "<ambiente>" + this.visionAmbiente.toXML() + "</ambiente>"; 
		cuadro += "<energia>" 
			+ Integer.toString(this.energia) +"</energia>";
		cuadro += "<promVarEneAvanz>"
				+ Float.toString(this.promedios[AvanzarAbajo.getInstancia().getIdentificador()]) 	+"</promVarEneAvanz>";
		cuadro += "<promVarEneComer>" 
				+ Float.toString(this.promedios[Comer.getInstancia().getIdentificador()]) +"</promVarEneComer>";
		cuadro += "<promVarEneLucha>" 
				+ Float.toString(this.promedios[Pelear.getInstancia().getIdentificador()]) +"</promVarEneLucha>";
		return cuadro + "</estado>";
	}
	
	public void actualizarEstado(Percepcion p) {
		this.visionAmbiente.actualizar(p);
		
		/* Cálculo de los promedios
		 * ultimaAccionEjecutada posee la última acción que el agente ejecutó :)
		 * Si es igual a null es porque nunca ejecutó una acción antes, por lo tanto
		 * no hay promedios que calcular.
		 * NACHO dice: Por que la seteas a null entonces luego de calcular un promedio??
		 */
		if (this.ultimaAccionEjecutada != null) {
			/* Variacion positiva significa PERDIDA
			 * Variación negativa significa GANANCIA
			 */
			int diferenciaEnergia = p.getEnergia() - this.energia;
			
			/* Suma es la sumatoria de diferencias de energía. */
			float suma = this.promedios[this.ultimaAccionEjecutada.getIdentificador()]
				* this.vecesEjecutada[this.ultimaAccionEjecutada.getIdentificador()];
			
			// Actualizo la cantidad de veces que se ejecutó la última acción
			int veces = this.vecesEjecutada[this.ultimaAccionEjecutada.getIdentificador()] + 1;
			this.vecesEjecutada[this.ultimaAccionEjecutada.getIdentificador()] = veces;
			
			// Ahora sí, recalculo el promedio
			suma += diferenciaEnergia;
			float promedioNuevo = (suma / veces);
			this.promedios[this.ultimaAccionEjecutada.getIdentificador()] = promedioNuevo;
			
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
		estadoClon.promedios = this.promedios.clone();
		estadoClon.vecesEjecutada = this.vecesEjecutada.clone();
		estadoClon.visionAmbiente = (VisionAmbiente) this.visionAmbiente.clone();
		
		return estadoClon;
	}
	
	public int getEnergia() {
		return energia;
	}
	
	public float getPromedioVarEnergia(Accion a) {
		return this.promedios[a.getIdentificador()];
	}
	
	public Accion getUltimaAccionEjecutada() {
		return this.ultimaAccionEjecutada;
	}
}
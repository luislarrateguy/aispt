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

import tpsia.tp1.Ambiente;
import tpsia.tp1.EstadoCelda;
import tpsia.tp1.FuncionesUtiles;
import tpsia.tp1.Percepcion;

public class VisionAmbiente extends Ambiente {
	
	/**
	 * Utilizada para saber si la posición del pacman recibida en la percepción
	 * es válida.
	 */
	public static boolean first = true;
	
	public VisionAmbiente() {
		super();
		
		for (int i=0;i<4;i++)
			for (int j=0;j<4;j++)
				this.tablero[i][j] = EstadoCelda.Desconocida;
	}
	
	public boolean conoceTodo() {
		for (int i=0;i<4;i++) {
			for (int j=0;j<4;j++) {
				if (this.tablero[i][j] == EstadoCelda.Desconocida)
					return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Este método sólo tiene sentido si se conoce todo el ambiente.
	 * @return
	 */
	public boolean hayAlimentosSinComer() {
		for (int i=0;i<4;i++) {
			for (int j=0;j<4;j++) {
				if (this.tablero[i][j] == EstadoCelda.Comida)
					return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Este método sólo tiene sentido si se conoce todo el ambiente.
	 * @return
	 */
	public boolean hayEnemigosVivos() {
		for (int i=0;i<4;i++) {
			for (int j=0;j<4;j++) {
				if (this.tablero[i][j] == EstadoCelda.Enemigo)
					return true;
			}
		}
		
		return false;
	}
	
	/**
	 * La única actualización que se realiza en VisionAmbiente son
	 * las nuevas celdas descubiertas por el agente. Los demás cambios
	 * se realizan por medio de las acciones sobre VisionAmbiente.
	 * @param p
	 */
	public void actualizar(Percepcion p) {	
		if (VisionAmbiente.first) {
			this.posicionPacman[0] = p.getPosX();
			this.posicionPacman[1] = p.getPosY();
			VisionAmbiente.first = false;
		}
		
		int x,y;
		EstadoCelda[] celdasAdyacentes = p.getCeldasAdyacentes();
		
		// Celda adyacente de arriba
		x = this.posicionPacman[0];
		y = FuncionesUtiles.sumarPosiciones(this.posicionPacman[1], 1);
		this.tablero[x][y] = celdasAdyacentes[0];
		
		// Celda adyacente de abajo
		x = this.posicionPacman[0];
		y = FuncionesUtiles.sumarPosiciones(this.posicionPacman[1], -1);
		this.tablero[x][y] = celdasAdyacentes[1];
		
		// Celda adyacente de derecha
		x = FuncionesUtiles.sumarPosiciones(this.posicionPacman[0], 1);
		y = this.posicionPacman[1];
		this.tablero[x][y] = celdasAdyacentes[2];
		
		// Celda adyacente de izquierda
		x = FuncionesUtiles.sumarPosiciones(this.posicionPacman[0], -1);
		y = this.posicionPacman[1];
		this.tablero[x][y] = celdasAdyacentes[3];
	}

	public String draw() {
		String aux = "Vision del ambiente:\n";
		aux += super.draw();
		
		aux += "posPacman: [" 
			+ Integer.toString(posicionPacman[0]) +","
			+ Integer.toString(posicionPacman[1]) + "]";
		
		return aux;
	}
	
	@Override
	public String toXML() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object clone() {
		VisionAmbiente visionAmbienteClon = new VisionAmbiente();
		
		/* Le copio el tablero y la posición del agente (estado de la
		 * clase Ambiente). */
		this.copiarEstadoA(visionAmbienteClon);
		
		// Le copio el estado propio de VisionAmbiente
		//Esto no va. Es un atributo de clase, o sea que afecta a todas las
		//instancias. no hace falta copiarlo, ni se puede en realidad.
		//visionAmbienteClon.first = this.first;
		
		return visionAmbienteClon;
	}
	
	@Override
	public boolean equals(Object o) {
		if (! (o instanceof VisionAmbiente))
			return false;
		
		VisionAmbiente va = (VisionAmbiente)o;
		
		if (!super.equals(va))
			return false;
		
		// lo mismo aca, no se puede acceder desde el objeto
		//sino desde la clase. Debia ser VisionAmbiente.first
		//if (this.first != va.first)
		//	return false;
		
		return true;
	}
}
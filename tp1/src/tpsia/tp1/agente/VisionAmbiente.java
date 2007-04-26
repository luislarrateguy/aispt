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

import java.util.HashMap;

import tpsia.tp1.EstadoCelda;
import tpsia.tp1.Ambiente;
import tpsia.tp1.FuncionesUtiles;
import tpsia.tp1.Offset;
import tpsia.tp1.Percepcion;
import calculador.Pair;

public class VisionAmbiente extends Ambiente {

	public VisionAmbiente() {
		super();
		
		for (int i=0;i<4;i++)
			for (int j=0;j<4;j++)
				this.tablero[i][j] = EstadoCelda.Desconocida;
	}
	@Override
	public void comer() {
		// TODO Auto-generated method stub
	}

	@Override
	public void moverPacman(Offset o) {
		this.posicionPacman[0] = Math.abs(this.posicionPacman[0] + o.x())%4;
		this.posicionPacman[1] = Math.abs(this.posicionPacman[1] + o.y())%4;
		
		this.posicionPacman[0] = Math.abs(this.posicionPacman[0] + o.x())%4;
		this.posicionPacman[1] = Math.abs(this.posicionPacman[1] + o.y())%4;
	}

	@Override
	public void pelear() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * La única actualización que se realiza en VisionAmbiente son
	 * las nuevas celdas descubiertas por el agente. Los demás cambios
	 * se realizan por medio de las acciones sobre VisionAmbiente.
	 * @param p
	 */
	public void actualizarEstado(Percepcion p) {
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
		String aux = super.draw();
		
		aux += "posPacman: [" 
			+ Integer.toString(posicionPacman[0]) +","
			+ Integer.toString(posicionPacman[1]) + "]\n";
		
		return aux;
	}
	
	@Override
	public String toXML() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
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

package tpsia.tp1;

import java.util.Vector;

import calculador.Pair;

public class AmbienteReal implements IAmbiente {

	private int energiaPacman;
	private EstCelda[][] tablero;
	private int[] pos;
	private boolean posicionInicial;
	

	public AmbienteReal() {
		super();
		this.posicionInicial = false;	
		this.tablero = new EstCelda[4][4];
		for (int i=0;i<4;i++)
			for (int j=0;j<4;j++)
				this.tablero[i][j] = EstCelda.Vacia;
		
		this.energiaPacman = 0;
		this.pos = new int[2];
		this.pos[0] = 0;
		this.pos[1] = 0;
	}

	public EstCelda[] getCeldasAdyacentes() {
		EstCelda[] ady = new EstCelda[4];
		/* arr[0] aba[1] der[2] izq[3] */ 
		int x;
		int y;

		// arr
		x = this.pos[0];
		y = (this.pos[1]+1)%4;
		ady[0] = this.tablero[x][y];
		// aba
		x = this.pos[0];
		y = Math.abs(this.pos[1]-1)%4;
		ady[1] = this.tablero[x][y];
		// der
		x = (this.pos[0]+1)%4;
		y = this.pos[1];
		ady[2] = this.tablero[x][y];
		// izq
		x = Math.abs(this.pos[0]-1) % 4;
		y = this.pos[1];
		ady[3] = this.tablero[x][y];
		
		return ady.clone();
	}
	public void inicializar(int enePacman, Pair posicionPacMan, 
			Vector posicionesEnemigos, Vector posicionesComida) {
		// Inicializando PacMan
		this.energiaPacman = enePacman;
		this.pos[0] = posicionPacMan.x()-1;
		this.pos[1] = posicionPacMan.y()-1;
		
		//Inicializando enemigos
		
		//Inicializando comida
		
		
	}
	public int energiaPacmanActual() {
		return energiaPacman;
	}

	public int[] getPosIniPacman() {
		if (!posicionInicial) {
			posicionInicial = true;
			return pos.clone();
		}
		return null;
	}

	public void actualizar(int enePacman) {
		energiaPacman = enePacman;
	}

	public void moverPacman(Offset o) {
		this.pos[0] = Math.abs((this.pos[0] + o.x()))%4;
		this.pos[1] = Math.abs((this.pos[1] + o.y()))%4;
	}

	public void comer() {
		// TODO ChequearPrecondición
		// Si no se cumple (no hay comida) lanzar excepción o algo
		// ejecutar la accion.
		
	}

	public void pelear() {
		// TODO ChequearPrecondición
		// Si no se cumple (no hay enemigo) lanzar excepción o algo
		// ejecutar la accion.
	}
	public String toString() {
		return null;
	}
	public String draw() {
		String cuadro = new String("\n");
		for (int j=0;j<4;j++) {
			for (int i=0;i<4;i++) {
				cuadro 	+= "[ "
						+Integer.toString(this.tablero[i][j].valor()) 
						//+ this.ambiente[i][j]
						+	" ]";
			}
			cuadro += "\n";
		}
		cuadro += "posPacman: [" 
				+ Integer.toString(pos[0]) +","
				+ Integer.toString(pos[1]) + "]\n";
		cuadro += "energia:" 
			+ Integer.toString(this.energiaPacman) 	+"\n";
		return cuadro;
	}

	public String toXML() {
		// TODO Auto-generated method stub
		return null;
	}



}
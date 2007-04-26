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

import java.util.Hashtable;
import java.util.Vector;

import calculador.Pair;

public class AmbienteReal extends Ambiente {
	/**
	 * Para saber cuando enviar la posición del Pacman.
	 * Esta sólo se debe enviar en la primer percepción.
	 */
	private boolean posicionInicial;
	
	private int energiaPacman;

	public AmbienteReal() {
		super();
		
		for (int i=0;i<4;i++)
			for (int j=0;j<4;j++)
				this.tablero[i][j] = EstadoCelda.Vacia;
		
		this.posicionInicial = false;
	}

	public EstadoCelda[] getCeldasAdyacentes() {
		EstadoCelda[] ady = new EstadoCelda[4];
		/* arr[0] aba[1] der[2] izq[3] */ 
		int x;
		int y;

		// Celda de arriba del pacman
		x = this.posicionPacman[0];
		y = FuncionesUtiles.sumarPosiciones(this.posicionPacman[1], 1);
		ady[0] = this.tablero[x][y];
		
		// Celda de abajo
		x = this.posicionPacman[0];
		y = FuncionesUtiles.sumarPosiciones(this.posicionPacman[1], -1);
		ady[1] = this.tablero[x][y];
		
		// Celda de la derecha
		x = FuncionesUtiles.sumarPosiciones(this.posicionPacman[0], 1);
		y = this.posicionPacman[1];
		ady[2] = this.tablero[x][y];
		
		// Celda de la izquierda
		x = FuncionesUtiles.sumarPosiciones(this.posicionPacman[0], -1);
		y = this.posicionPacman[1];
		ady[3] = this.tablero[x][y];
		
		//return ady.clone();
		return ady;
	}
	
	/**
	 * Recibe los datos del simulador (y éste del calculador) e inicializa
	 * el ambiente real.
	 * @param energiaPacman
	 * @param posicionPacMan
	 * @param posEnemigos
	 * @param posComida
	 */
	@SuppressWarnings("unchecked")
	public void inicializar(int energiaPacman, Pair posicionPacMan, 
			Vector posEnemigos, Vector posComida) {
		
		// Inicializando PacMan
		this.energiaPacman = energiaPacman;
		
		this.setPosicionPacman(posicionPacMan.getX(), posicionPacMan.getY());
		
		//Inicializando enemigos
		Vector<Pair> posicionesEnemigos = (Vector<Pair>)posEnemigos;
		for (Pair unaCelda : posicionesEnemigos)
			this.tablero[unaCelda.getX()][unaCelda.getY()] = EstadoCelda.Enemigo;
		
		//Inicializando comida
		Vector<Pair> posicionesComida = (Vector<Pair>)posComida;
		for (Pair unaCelda : posicionesComida)
			this.tablero[unaCelda.getX()][unaCelda.getY()] = EstadoCelda.Comida;
	}

	public int[] getPosIniPacman() {
		if (!posicionInicial) {
			posicionInicial = true;
			return posicionPacman.clone();
		}
		
		return null;
	}

	public void moverPacman(Offset o) {
		this.posicionPacman[0] = FuncionesUtiles.sumarPosiciones(this.posicionPacman[0], o.x());
		this.posicionPacman[1] = FuncionesUtiles.sumarPosiciones(this.posicionPacman[1], o.y());
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
		String aux = super.draw();
		
		aux += "posPacman: [" 
			+ Integer.toString(posicionPacman[0]) +","
			+ Integer.toString(posicionPacman[1]) + "]\n";
		
		aux += "energia:" 
			+ Integer.toString(this.energiaPacman) 	+"\n";
		
		return aux;
	}

	public String toXML() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getEnergiaPacman() {
		return energiaPacman;
	}

	public void setEnergiaPacman(int energiaPacman) {
		this.energiaPacman = energiaPacman;
	}
}
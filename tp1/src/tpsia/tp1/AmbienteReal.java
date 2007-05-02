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

import tpsia.tp1.acciones.Comer;
import tpsia.tp1.acciones.Pelear;
import tpsia.tp1.agente.VisionAmbiente;

import calculador.Calculador;
import calculador.Pair;

public class AmbienteReal extends Ambiente {
	/**
	 * Para saber cuando enviar la posición del Pacman.
	 * Esta sólo se debe enviar en la primer percepción.
	 */
	private boolean posicionInicial;
	
	/**
	 * Energía actual del Pacman.
	 */
	private int energiaPacman;

	public AmbienteReal() {
		super();
		
		for (int i=0;i<4;i++)
			for (int j=0;j<4;j++)
				this.tablero[i][j] = EstadoCelda.Vacia;
		
		this.posicionInicial = false;
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
		
		this.posicionPacman[0] = posicionPacMan.getX();
		this.posicionPacman[1] = posicionPacMan.getY();
		
		//Inicializando enemigos
		Vector<Pair> posicionesEnemigos = (Vector<Pair>)posEnemigos;
		for (Pair unaCelda : posicionesEnemigos)
			this.tablero[unaCelda.getX()][unaCelda.getY()] = EstadoCelda.Enemigo;
		
		//Inicializando comida
		Vector<Pair> posicionesComida = (Vector<Pair>)posComida;
		for (Pair unaCelda : posicionesComida)
			this.tablero[unaCelda.getX()][unaCelda.getY()] = EstadoCelda.Comida;
	}

	public int[] getPosicionInicialPacman() {
		if (!posicionInicial) {
			posicionInicial = true;
			return posicionPacman.clone();
		}
		
		return null;
	}
	
	public String toString() {
		return null;
	}

	public String draw() {
		String aux = "Ambiente Real:\n";
		aux += super.draw();
		
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

	@Override
	public Object clone() {
		AmbienteReal ambienteRealClon = new AmbienteReal();
		
		/* Le copio el tablero y la posición del agente (estado de la
		 * clase Ambiente). */
		this.copiarEstadoA(ambienteRealClon);
		
		// Le copio el estado propio de AmbienteReal
		ambienteRealClon.posicionInicial = this.posicionInicial;
		ambienteRealClon.energiaPacman = this.energiaPacman;
		
		return ambienteRealClon;
	}
}
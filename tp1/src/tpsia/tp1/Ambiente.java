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


public abstract class Ambiente {
	protected EstadoCelda[][] tablero;
	protected int[] posicionPacman;
	
	public Ambiente() {
		this.tablero = new EstadoCelda[4][4];
		
		/* posicionPacman[0] hace mención a las filas, y
		 * el otro a las columnas. */
		this.posicionPacman = new int[2];
		this.posicionPacman[0] = 0;
		this.posicionPacman[1] = 0;
	}
	
	public abstract void moverPacman(Offset o);
	public abstract void comer();
	public abstract void pelear();
	
	public boolean hayEnemigo(int x, int y) {
		return this.tablero[x][y].equals(EstadoCelda.Enemigo);
	}
	
	public boolean hayComida(int x, int y) {
		return this.tablero[x][y].equals(EstadoCelda.Comida);
	}
	
	/**
	 * Debe utilizarse este método al setear la posición del Pacman, así se
	 * matiene actualizado el tablero.
	 * @param x
	 * @param y
	 */
	public void setPosicionPacman(int x, int y) {
		this.posicionPacman[0] = x;
		this.posicionPacman[1] = y;
		//this.tablero[x][y] = EstadoCelda.Pacman;
	}
	
	//string toTurtle();
	
	public String draw() {
		String cuadro = new String("\n");
		
		Hashtable<Integer, String> aux;
		aux = new Hashtable<Integer, String>();
		aux.put(-1, "X");
		aux.put(0, " ");
		aux.put(1, "C");
		aux.put(2, "E");
		aux.put(3, "P");
		
		for (int j=0;j<4;j++) {
			for (int i=0;i<4;i++) {
				cuadro 	+= "[ "
						+aux.get(this.tablero[i][j].valor())
						//+ this.ambiente[i][j]
						+	" ]";
			}
			cuadro += "\n";
		}
		
		return cuadro;
	}
	
	public abstract String toXML();
}
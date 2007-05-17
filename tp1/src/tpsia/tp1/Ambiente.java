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


public abstract class Ambiente implements Cloneable {
	protected EstadoCelda[][] tablero;
	protected int[] posicionPacman;
	protected Hashtable<Integer, String> aux;
	
	public Ambiente() {
		this.tablero = new EstadoCelda[4][4];
		
		/* posicionPacman[0] hace mención a las filas, y
		 * el otro a las columnas. */
		this.posicionPacman = new int[2];
		this.posicionPacman[0] = 0;
		this.posicionPacman[1] = 0;
		
		aux = new Hashtable<Integer, String>();
		aux.put(-1, "X");
		aux.put(0, " ");
		aux.put(1, "C");
		aux.put(2, "E");
		aux.put(3, "P");
	}
	
	public EstadoCelda[] getCeldasAdyacentes() {
		EstadoCelda[] ady = new EstadoCelda[4];
		/* arr[0] aba[1] der[2] izq[3] */ 
		int pos[];

		// Celda de arriba del pacman
		pos = FuncionesUtiles.sumarPosiciones(this.posicionPacman, Offset.Arriba);
		ady[0] = this.tablero[pos[0]][pos[1]];
		
		// Celda de abajo
		pos = FuncionesUtiles.sumarPosiciones(this.posicionPacman, Offset.Abajo);
		ady[1] = this.tablero[pos[0]][pos[1]];
		
		// Celda de la derecha
		pos = FuncionesUtiles.sumarPosiciones(this.posicionPacman, Offset.Derecha);
		ady[2] = this.tablero[pos[0]][pos[1]];
		
		// Celda de la izquierda
		pos = FuncionesUtiles.sumarPosiciones(this.posicionPacman, Offset.Izquierda);
		ady[3] = this.tablero[pos[0]][pos[1]];
		
		//return ady.clone();
		return ady;
	}
	
	public void pelear() {
		int x = this.posicionPacman[0];
		int y = this.posicionPacman[1];
		
		if (!this.hayEnemigo(x, y)) {
			Logging.logError("El pacman intentó pelear contra un enemigo " +
					"que no existe en (" + x + "," + y + ")");
			
			return;
		}
		
		this.tablero[x][y] = EstadoCelda.Vacia;
	}
	
	public void comer() {
		int x = this.posicionPacman[0];
		int y = this.posicionPacman[1];
		
		if (!this.hayComida(x, y)) {
			Logging.logError("El pacman intentó comer comida que no existe en (" +
					x + "," + y + ")");
			
			return;
		}
		
		this.tablero[x][y] = EstadoCelda.Vacia;
	}
	
	public void mover(Offset o) {
		// x
		this.posicionPacman[0] = FuncionesUtiles.sumarPosiciones(
				this.posicionPacman[0], o.x());
		
		// y
		this.posicionPacman[1] = FuncionesUtiles.sumarPosiciones(
				this.posicionPacman[1], o.y());
	}
	
	public boolean hayEnemigo(int x, int y) {
		return this.tablero[x][y].equals(EstadoCelda.Enemigo);
	}
	
	public boolean hayComida(int x, int y) {
		return this.tablero[x][y].equals(EstadoCelda.Comida);
	}
	
	public String toString() {
		return this.draw();
	}
	public String draw() {
		return draw(false);
	}
	public String draw(boolean p) {
		String cuadro = new String("\n[ ]  0   1   2   3 ->X\n");
		
		
		
		for (int j=0;j<4;j++) {
			cuadro += "  "+Integer.toString(j)+" ";
			for (int i=0;i<4;i++) {
				cuadro 	+= "["+aux.get(this.tablero[i][j].valor());
				if (p && posicionPacman[0]==i && posicionPacman[1] == j) {
					cuadro += "P]";
				} else {
					cuadro += " ]";
				}
			}
			cuadro += "\n";
		}
		
		return cuadro+" Y\n";
	}
	
	public  String toXML() {
		String str = new String("");
		return str;
	}
	
	public abstract Object clone();
	
	public void copiarEstadoA(Ambiente ambienteClon) {
		// Copio el tablero
		ambienteClon.tablero = new EstadoCelda[4][4];
		for (int i=0;i<4;i++)
			for (int j=0;j<4;j++)
				ambienteClon.tablero[i][j] = this.tablero[i][j];
		
		// Copio la posición del pacman
		ambienteClon.posicionPacman = new int[2];
		ambienteClon.posicionPacman[0] = this.posicionPacman[0];
		ambienteClon.posicionPacman[1] = this.posicionPacman[1];
	}

	public int[] getPosicionPacman() {
		return posicionPacman;
	}
	
	@Override
	public boolean equals(Object o) {
		if (! (o instanceof Ambiente))
			return false;
		
		Ambiente a = (Ambiente)o;
		
		// ¿Es igual el tablero de juego?
		for (int i=0;i<4;i++) {
			for (int j=0;j<4;j++) {
				if (this.tablero[i][j] != a.tablero[i][j])
					return false;
			}
		}
		
		// ¿Es igual la posición del pacman?
		if (this.posicionPacman[0] != a.posicionPacman[0] ||
				this.posicionPacman[1] != a.posicionPacman[1])
			return false;
		
		return true;
	}
	
	public int cantidadCeldasDesconocidas() {
		int cant = 0;
		
		for (int i=0;i<4;i++) {
			for (int j=0;j<4;j++) {
				if (this.tablero[i][j] == EstadoCelda.Desconocida)
					cant++;
			}
		}
		
		return cant;
	}
}
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

public class AmbienteReal implements IAmbiente {

	private int energiaPacman;
	private Matriz tablero;

	public Matriz getCeldasAdyacentes() {
		// TODO Auto-generated method stub
		return null;
	}

	public int energiaPacmanActual() {
		// TODO Auto-generated method stub
		return energiaPacman;
	}

	public int getPosXIniPacman() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Float getEnergiaIniPacman() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getPosYIniPacman() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void actualizar(int enePacman) {
		energiaPacman = enePacman;
	}

	public void moverPacman(OffsetX oX, OffsetY oY) {
		// TODO Actualizar la posición del  pacman en el ambiente
		// abajo ejemplo de como obtener el offset.
		oX.valor();
		oY.valor();
	}

	public void comer() {
		// TODO Auto-generated method stub
		
	}

	public void pelear() {
		// TODO Auto-generated method stub
		
	}

}
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

public class Percepcion {

	private EstCelda[] celdasAdyacentes;
	private int energia;
	private int posx;
	private int posy;


	public Percepcion(EstCelda[] ady, int e, 
			int[] posIniPacman) {
		Logging.logDebug("SIM: Armando percepcion...");
		this.celdasAdyacentes = ady;
		this.energia = e;
		if (posIniPacman != null) { 
			this.posx = posIniPacman[0];
			this.posy = posIniPacman[1];
		}
	}


	public EstCelda[] celdasAdyacentes() {
		return celdasAdyacentes.clone();
	}


	public int energia() {
		// TODO Auto-generated method stub
		return energia;
	}


	public int posXini() {
		// TODO Auto-generated method stub
		return posx;
	}
}
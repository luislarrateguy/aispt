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

package tpsia.tp2;

public class Percepcion {

	private EstadoCelda[] celdasAdyacentes;
	private int energia;
	private int posX = 0;
	private int posY = 0;
	private int tiempo;
	
	public Percepcion(EstadoCelda[] ady, int e, 
			int[] posIniPacman) {
		
		this.celdasAdyacentes = new EstadoCelda[4];
		for (int i=0; i<4; i++)
			this.celdasAdyacentes[i] = ady[i];
		
		this.energia = e;
		
		if (posIniPacman != null) { 
			this.posX = posIniPacman[0];
			this.posY = posIniPacman[1];
		}
	}
	
	/**
	 * Devuelve la percepción en formato string, como una sentencia Prolog.
	 */
	@Override
	public String toString() {
		StringBuffer resultado = new StringBuffer("percepcion([");
		
		// Celdas adyacentes
		resultado.append(this.celdasAdyacentes[0].toString());
		for (int i=1; i<4; i++) {
			resultado.append(",");
			resultado.append(this.celdasAdyacentes[i].toString());
		}
		resultado.append("],");
		
		// Posición del agente
		resultado.append(this.posX);
		resultado.append(",");
		resultado.append(this.posY);
		resultado.append(",");
		
		// Energía del agente
		resultado.append(this.energia);
		resultado.append(",");
		
		// Tiempo
		resultado.append(this.tiempo);
		
		resultado.append(")");
		
		return resultado.toString();
	}

	public EstadoCelda[] getCeldasAdyacentes() {
		return celdasAdyacentes.clone();
	}

	public int getEnergia() {
		return energia;
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	public int getTiempo() {
		return tiempo;
	}

	public void setTiempo(int tiempo) {
		this.tiempo = tiempo;
	}
}
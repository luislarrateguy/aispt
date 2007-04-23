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

import java.text.Format;
import java.util.HashMap;
import java.util.Vector;

import tpsia.tp1.EstCelda;
import tpsia.tp1.IAmbiente;
import tpsia.tp1.OffsetX;
import tpsia.tp1.OffsetY;

import calculador.Pair;
/**
 * 
 * @author nacho
 * @deprecated Como charlamos Estado implementará la interfaz
 * ya que se complicaría la duplicacion de los estados, busqueda
 * y demás.
 */
public class VisionAmbiente implements IAmbiente {

	private HashMap<Pair, EstCelda> tablero;
	private Pair ppac;
	
	public VisionAmbiente() {
		this.tablero = new HashMap<Pair, EstCelda>(16);
		this.ppac = new Pair(0,0);
	}
	public void moverPacman(OffsetX oX, OffsetY oY) {
		// TODO Actualizar la posición del pacman en la vision
		// del ambiente
		ppac.inicializar( (ppac.x() + oX.valor()) % 4 + 1, 
				(ppac.y() + oY.valor()) % 4 + 1);
	}
	public EstCelda getEstadoCelda(Pair cel) {
		return this.tablero.get(cel);
	}
	public String verComoTablero() {
		String tab = new String("");
		for (int i=0; i<16; i++) {
			if ((i % 4) == 0) {
				tab += "[ " + tablero.get(i).valor() + " ] ";
			}
		}
		return tab;
	}
}
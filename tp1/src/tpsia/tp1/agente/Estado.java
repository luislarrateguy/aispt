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

import java.io.Serializable;

import tpsia.tp1.IAmbiente;
import tpsia.tp1.OffsetX;
import tpsia.tp1.OffsetY;

public class Estado implements IAmbiente {

	/**
	 * 
	 */
	private int energia;
	private float energiaPromedio;
	private VisionAmbiente visionAmb;
	public Estado() {
		this.energia = 0;
		this.energiaPromedio =  (float) 0.00;
		this.visionAmb = new VisionAmbiente();
	}
	public void moverPacman(OffsetX oX, OffsetY oY) {
		// TODO Auto-generated method stub
		
	}
	
}
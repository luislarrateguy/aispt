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

import tpsia.tp1.EstCelda;
import tpsia.tp1.IAmbiente;
import tpsia.tp1.Offset;
import calculador.Pair;

public class Estado implements IAmbiente {


	private int energia;
	private float promVarEneLucha;
	private float promVarEneComer;
	private float promVarEneAvanz;
	private Pair posPacman;
	private EstCelda[][] ambiente;
	
	public Estado() {
		this.ambiente = new EstCelda[4][4];
		this.energia = 0;
		this.promVarEneLucha =  (float) 0.00;
		this.promVarEneComer =  (float) 0.00;
		this.promVarEneAvanz =  (float) 0.00;
	}
	public void moverPacman(Offset o) {
		this.posPacman.inicializar((posPacman.x() + o.x())%4, 
				(posPacman.y() + o.y())%4);
	}
	/*
	 * Estas dos podrian estar unidas
	 * Y OffSet podría ser de un sólo tipo.
	 */
	public boolean hayEnemigo(Offset o) {
		return this.ambiente[(posPacman.x()+o.x())%4][(posPacman.y()+o.y())%4]
		                                               .equals(EstCelda.Enemigo);
	}
	
	public boolean hayComida(Offset o) {
		return this.ambiente[(posPacman.x()+o.x())%4][(posPacman.y()+o.y())%4]
		                                              .equals(EstCelda.Comida);
	}
	
	public void comer() {
		// TODO Auto-generated method stub
		
	}
	public void pelear() {
		// TODO Auto-generated method stub
		
	}
}
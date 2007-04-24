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
import tpsia.tp1.Percepcion;

public class Estado implements IAmbiente {


	private int energia;
	private float promVarEneLucha;
	private float promVarEneComer;
	private float promVarEneAvanz;
	private int[] pos;
	
	private EstCelda[][] ambiente;
	
	public Estado() {
		this.ambiente = new EstCelda[4][4];
		for (int i=0;i<4;i++)
			for (int j=0;j<4;j++)
				this.ambiente[i][j] = EstCelda.Desconocida;
		this.energia = 0;
		this.pos = null;
		this.promVarEneLucha =  (float) 0.00;
		this.promVarEneComer =  (float) 0.00;
		this.promVarEneAvanz =  (float) 0.00;
	}
	public void moverPacman(Offset o) {
		this.pos[0] = Math.abs(this.pos[0] + o.x())%4;
		this.pos[1] = Math.abs(this.pos[1] + o.y())%4;
	}
	/*
	 * Estas dos podrian estar unidas
	 * Y OffSet podría ser de un sólo tipo.
	 */
	public boolean hayEnemigo(Offset o) {
		return this.ambiente[Math.abs(pos[0]+o.x())%4][Math.abs(pos[1]+o.y())%4]
		                                               .equals(EstCelda.Enemigo);
	}
	
	public boolean hayComida(Offset o) {
		return this.ambiente[Math.abs(pos[0]+o.x())%4][Math.abs(pos[1]+o.y())%4]
		                                              .equals(EstCelda.Comida);
	}
	
	public void comer() {
		// TODO Auto-generated method stub
		
	}
	public void pelear() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	public String draw() {
		String cuadro = new String("\n");
		for (int j=0;j<4;j++) {
			for (int i=0;i<4;i++) {
				cuadro 	+= "[ "
						+Integer.toString(this.ambiente[i][j].valor()) 
						//+ this.ambiente[i][j]
						+	" ]";
			}
			cuadro += "\n";
		}
		cuadro += "posPacman: [" 
				+ Integer.toString(pos[0]) +","
				+ Integer.toString(pos[1]) + "]\n";
		cuadro += "energia:" 
			+ Integer.toString(this.energia) 	+"\n";
		cuadro += "promVarEneAvanz:" 
				+ Float.toString(this.promVarEneAvanz) 	+"\n";
		cuadro += "promVarEneAvanz:" 
				+ Float.toString(this.promVarEneComer) +"\n";
		cuadro += "promVarEneLucha:" 
				+ Float.toString(this.promVarEneLucha) +"\n";
		return cuadro;
	}
	public String toXML() {
		// TODO Auto-generated method stub
		return null;
	}
	public void actualizarEstado(Percepcion p) {
		if (this.pos == null) {
			this.pos = new int[2];
			this.pos[0] = p.posXini();
			this.pos[1] = p.posXini();
		}
		this.energia = p.energia();
		EstCelda[] ady = p.celdasAdyacentes();
		/* arr[0] aba[1] der[2] izq[3] */ 
		int x;
		int y;

		// arr
		x = this.pos[0];
		y = (this.pos[1]+1)%4;
		this.ambiente[x][y] = ady[0];
		// aba
		x = this.pos[0];
		y = Math.abs(this.pos[1]-1)%4;
		this.ambiente[x][y] = ady[1];
		// der
		x = (this.pos[0]+1)%4;
		y = this.pos[1];
		this.ambiente[x][y] = ady[2];
		// izq
		x = Math.abs(this.pos[0]-1)%4;
		y = this.pos[1];
		this.ambiente[x][y] = ady[3];		                
	}
}
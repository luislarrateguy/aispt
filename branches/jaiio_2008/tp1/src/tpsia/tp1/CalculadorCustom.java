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

import java.util.Vector;

import calculador.Calculador;
import calculador.Pair;

public class CalculadorCustom extends Calculador {
	private final int[] POSICION_INICIAL = {0,0};
	private final int[] POSICION_COMIDAS = {
			0,0,
			0,1,
			0,2,
			0,3,
			1,0,
			1,1,
			1,2,
			1,3,
			2,0,
			2,2,
			2,3,
			3,0,
			3,1,
			3,2,
			3,3
	};
	
	private final int[] POSICION_ENEMIGOS = {
			2,1,
	};
	
	/** Energía inicial del pacman. */
	private final int ENERGIA_INICIAL = 14;
	
	/** Energía que gana el pacman por alimento. */
	private final int ENERGIA_COMIDA = 5;
	/** Energía que pierde el pacman por pelear. */
	private final int ENERGIA_ENEMIGO = 5;
	/** Energía que pierde el pacman por moverse. */
	private final int ENERGIA_MOVIMIENTO = 1;
	
	private int energiaPacman;
	
	private Vector posicionEnemigos;
	private Vector posicionComida;
	private Pair posicionInicial;
	
	public CalculadorCustom(String str) {
		this();
	}
	
	@SuppressWarnings("unchecked")
	public CalculadorCustom() {
		this.energiaPacman = ENERGIA_INICIAL;
		
		// Posiciones
		this.posicionInicial = new Pair(POSICION_INICIAL[0]+1, POSICION_INICIAL[1]+1);
		
		Pair aux;
		this.posicionComida = new Vector();
		for (int i=0; i<POSICION_COMIDAS.length; i += 2) {
			aux = new Pair(POSICION_COMIDAS[i]+1, POSICION_COMIDAS[i+1]+1);
			this.posicionComida.add(aux);
		}
		
		this.posicionEnemigos = new Vector();
		for (int i=0; i<POSICION_ENEMIGOS.length; i += 2) {
			aux = new Pair(POSICION_ENEMIGOS[i]+1, POSICION_ENEMIGOS[i+1]+1);
			this.posicionEnemigos.add(aux);
		}
	}
	
	@Override
	public int calcularEnergiaPacMan() {
		return this.energiaPacman;
	}
	
	@Override
	public int calcularEnergiaPacMan(String accion) {
		if (accion.equals("arriba") ||
				accion.equals("abajo") ||
				accion.equals("derecha") ||
				accion.equals("izquierda"))
			
			this.energiaPacman -= ENERGIA_MOVIMIENTO;
		
		else if (accion.equals("comer"))
			this.energiaPacman += ENERGIA_COMIDA;
		
		else if (accion.equals("pelear"))
			this.energiaPacman -= ENERGIA_ENEMIGO;
		
		return this.energiaPacman;
	}
	
	@Override
	public Pair getPosicionInicial() {
		return this.posicionInicial;
	}
	
	@Override
	public Vector inicializarComida() {
		return this.posicionComida;
	}
	
	@Override
	public Vector<Pair> inicializarEnemigo() {
		return this.posicionEnemigos;
	}
}

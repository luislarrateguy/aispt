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

public class FuncionesUtiles {
	/**
	 * Suma una cantidad 'incremento' a la 'posicion' dada.
	 * @param posicion
	 * @param incremento
	 * @return
	 */
	public static int sumarPosiciones (int posicion, int incremento) {
		
		if ( (posicion + incremento) >= 0)
			return ( (posicion + incremento) % 4);
		
		// Esto no está muy bien, pero no hay problemas para el simulador
		return (4 + (posicion + incremento));
	}
	
	/**
	 * Recibe una posición y un offset. Retorna la posición mas el offset.
	 * @param posicionActual
	 * @param o
	 * @return
	 */
	public static int[] sumarPosiciones (int[] posicionActual, Offset o) {
		int[] resultado = new int[2];
		
		resultado[0] = sumarPosiciones(posicionActual[0], o.x());
		resultado[1] = sumarPosiciones(posicionActual[1], o.y());
		
		return resultado;
	}
}

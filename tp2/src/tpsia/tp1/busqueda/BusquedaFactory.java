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
package tpsia.tp1.busqueda;

import java.util.ArrayList;

import tpsia.tp1.agente.Estado;
import tpsia.tp1.agente.IObjetivo;
import tpsia.tp1.agente.VisionAmbiente;
/**
 * Implementa Factory para Búsquedas
 * @author Pividori M, Larrateguy L.
 *
 */
public class BusquedaFactory {
	public static Busqueda Create(String tipoBusqueda, Estado estado, IObjetivo objetivo, ArrayList<VisionAmbiente> estadosAlcanzados) {
		Busqueda b;
		if (tipoBusqueda.compareToIgnoreCase("aestrella") == 0)
				b = new BusquedaAEstrella(estado, objetivo, estadosAlcanzados);
		else if (tipoBusqueda.compareToIgnoreCase("avara") == 0) 
				b = new BusquedaAvara(estado, objetivo, estadosAlcanzados);
		else if (tipoBusqueda.compareToIgnoreCase("profundidad") == 0)
				b = new BusquedaProfundidad(estado, objetivo, estadosAlcanzados);
		else if (tipoBusqueda.compareToIgnoreCase("costouniforme") == 0)
			b = new BusquedaCostoUniforme(estado, objetivo, estadosAlcanzados);
		else if (tipoBusqueda.compareToIgnoreCase("amplitud") == 0)
			b = new BusquedaAmplitud(estado, objetivo, estadosAlcanzados);
		else
			b = null;

		return b;
		
	}
}

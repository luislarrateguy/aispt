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

import tpsia.tp1.Estado;
import tpsia.tp1.Logging;
import tpsia.tp1.acciones.AvanzarDerecha;
import tpsia.tp1.acciones.IAccion;

public class BusquedaCostoUniforme implements IBusqueda {

	public boolean cumpleObjetivo(Estado x) {
		// TODO Auto-generated method stub
		return false;
	}

	public ArrayList<IAccion> buscarSolucion(Estado p) {
		// TODO buscar secuencia de acciones
		Logging.logDebug("PACMAN: buscar accion");
		ArrayList<IAccion> l = new ArrayList<IAccion>();
		l.add(AvanzarDerecha.getInstancia());
		return l;
	}

}

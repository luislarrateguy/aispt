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

package tpsia.tp1.acciones;

import tpsia.tp1.Ambiente;
import tpsia.tp1.agente.Estado;

public class Pelear extends Accion {

	private static Pelear instancia;

	@Override
	public void ejecutar(Ambiente amb) throws Exception {
		amb.pelear();
	}

	@Override
	public String getTipoAccion() {
		return "pelear";
	}
	
	@Override
	public int getCosto() {
		return 40;
	}

	static public Accion getInstancia() {
		if (instancia == null) {
			instancia = new Pelear();
			Accion.acciones.add(instancia);
		}
		return instancia;
	}

	@Override
	public boolean aplicable(Estado estado) {
		/* Verifico que exista un enemigo en la posición del agente, y que
		 * haya energía suficiente para pelear (para esto último se tiene en
		 * cuenta una previsión). */
		if (estado.getAmbiente().hayEnemigo() &&
				(estado.getEnergia() + 
						estado.getPromedioVarEnergia(Pelear.getInstancia())) > 3)

			return true;
		
		return false;
	}
	
	@Override
	public int getIdentificador() {
		return 2;
	}
}
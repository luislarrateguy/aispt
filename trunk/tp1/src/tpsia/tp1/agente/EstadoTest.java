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

import static org.junit.Assert.*;

import org.junit.Test;

import tpsia.tp1.EstadoCelda;
import tpsia.tp1.Percepcion;
import tpsia.tp1.acciones.*;


public class EstadoTest {

	@Test
	public void testActualizarEstado() {
		AvanzarAbajo.getInstancia();
		Pelear.getInstancia();
		Comer.getInstancia();
		
		Estado estado = new Estado();
		
		Percepcion p = new Percepcion(new EstadoCelda[4], 40, new int[2]);
		Accion a = AvanzarAbajo.getInstancia();
		
		estado.actualizarEstado(p);
		assertEquals(40, estado.getEnergia());
		assertEquals((float)0.0, estado.getPromedioVarEnergia(AvanzarAbajo.getInstancia()));
		assertEquals((float)(0.0-5.0), estado.getPromedioVarEnergia(Pelear.getInstancia()));
		assertEquals((float)(0.0+5.0), estado.getPromedioVarEnergia(Comer.getInstancia()));
		
		// 1
		estado.ejecutarAccion(a);
		assertSame(AvanzarAbajo.getInstancia(), estado.getUltimaAccionEjecutada());
		
		p = new Percepcion(new EstadoCelda[4], 35, new int[2]);
		estado.actualizarEstado(p);
		assertEquals(35, estado.getEnergia());
		assertEquals((float)-5.0, estado.getPromedioVarEnergia(AvanzarAbajo.getInstancia()));
		assertEquals((float)(0.0-5.0), estado.getPromedioVarEnergia(Pelear.getInstancia()));
		assertEquals((float)(0.0+5.0), estado.getPromedioVarEnergia(Comer.getInstancia()));
		
		// 2
		estado.ejecutarAccion(a);
		assertSame(AvanzarAbajo.getInstancia(), estado.getUltimaAccionEjecutada());
		
		p = new Percepcion(new EstadoCelda[4], 32, new int[2]);
		estado.actualizarEstado(p);
		assertEquals(32, estado.getEnergia());
		assertEquals((float)-4.0, estado.getPromedioVarEnergia(AvanzarAbajo.getInstancia()));
		assertEquals((float)(0.0-5.0), estado.getPromedioVarEnergia(Pelear.getInstancia()));
		assertEquals((float)(0.0+5.0), estado.getPromedioVarEnergia(Comer.getInstancia()));
		
		// 3
		estado.ejecutarAccion(a);
		assertSame(AvanzarAbajo.getInstancia(), estado.getUltimaAccionEjecutada());
		
		p = new Percepcion(new EstadoCelda[4], 25, new int[2]);
		estado.actualizarEstado(p);
		assertEquals(25, estado.getEnergia());
		assertEquals((float)-5.0, estado.getPromedioVarEnergia(AvanzarAbajo.getInstancia()));
		assertEquals((float)(0.0-5.0), estado.getPromedioVarEnergia(Pelear.getInstancia()));
		assertEquals((float)(0.0+5.0), estado.getPromedioVarEnergia(Comer.getInstancia()));
	}
}

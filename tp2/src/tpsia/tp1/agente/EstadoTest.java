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

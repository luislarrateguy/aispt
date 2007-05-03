package tpsia.tp1.agente;

//import static org.junit.Assert.*;

//import org.junit.Test;


public class EstadoTest {/*

	@Test
	public void testActualizarEstado() {
		Estado estado = new Estado();
		
		Percepcion p = new Percepcion(new EstadoCelda[4], 40, new int[2]);
		IAccion a = AvanzarAbajo.getInstancia();
		
		estado.actualizarEstado(p);
		assertEquals(40, estado.getEnergia());
		assertEquals((float)0.0, estado.getPromEnergiaPerdidaAvanzar());
		assertEquals((float)0.0, estado.getPromedioEnergiaPerdidaPelear());
		assertEquals((float)0.0, estado.getPromedioEnergiaPerdidaComer());
		
		// 1
		estado.ejecutarAccion(a);
		assertSame(Avanzar.class, estado.getUltimaAccionEjecutada());
		
		p = new Percepcion(new EstadoCelda[4], 35, new int[2]);
		estado.actualizarEstado(p);
		assertEquals(35, estado.getEnergia());
		assertEquals((float)-5.0, estado.getPromEnergiaPerdidaAvanzar());
		assertEquals((float)0.0, estado.getPromedioEnergiaPerdidaPelear());
		assertEquals((float)0.0, estado.getPromedioEnergiaPerdidaComer());
		
		// 2
		estado.ejecutarAccion(a);
		assertSame(Avanzar.class, estado.getUltimaAccionEjecutada());
		
		p = new Percepcion(new EstadoCelda[4], 32, new int[2]);
		estado.actualizarEstado(p);
		assertEquals(32, estado.getEnergia());
		assertEquals((float)-4.0, estado.getPromEnergiaPerdidaAvanzar());
		assertEquals((float)0.0, estado.getPromedioEnergiaPerdidaPelear());
		assertEquals((float)0.0, estado.getPromedioEnergiaPerdidaComer());
		
		// 3
		estado.ejecutarAccion(a);
		assertSame(Avanzar.class, estado.getUltimaAccionEjecutada());
		
		p = new Percepcion(new EstadoCelda[4], 25, new int[2]);
		estado.actualizarEstado(p);
		assertEquals(25, estado.getEnergia());
		assertEquals((float)-5.0, estado.getPromEnergiaPerdidaAvanzar());
		assertEquals((float)0.0, estado.getPromedioEnergiaPerdidaPelear());
		assertEquals((float)0.0, estado.getPromedioEnergiaPerdidaComer());
	}
*/
}

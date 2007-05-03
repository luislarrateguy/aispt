package tpsia.tp1;

//import static org.junit.Assert.*;

//import org.junit.Test;

public class FuncionesUtilesTest {

	//@Test
	public void testSumarPosiciones1() {/*
		// Movimientos a la izquierda o abajo (-1)
		assertEquals("0,-1 -> 3", 3, FuncionesUtiles.sumarPosiciones(0, -1));
		assertEquals("1,-1 -> 0", 0, FuncionesUtiles.sumarPosiciones(1, -1));
		assertEquals("2,-1 -> 1", 1, FuncionesUtiles.sumarPosiciones(2, -1));
		assertEquals("3,-1 -> 2", 2, FuncionesUtiles.sumarPosiciones(3, -1));
		
		// Movimientos a la derecha o arriba (1)
		assertEquals("0,1 -> 1", 1, FuncionesUtiles.sumarPosiciones(0, 1));
		assertEquals("1,1 -> 2", 2, FuncionesUtiles.sumarPosiciones(1, 1));
		assertEquals("2,1 -> 3", 3, FuncionesUtiles.sumarPosiciones(2, 1));
		assertEquals("3,1 -> 0", 0, FuncionesUtiles.sumarPosiciones(3, 1));
		*/
		
	}
	
	//@Test
	public void testSumarPosiciones2() {/*
		// Pruebas para el método sumarPosiciones(int[], Offset)
		int[] pos = new int[2];
		int[] posResultado = new int[2];
		int[] devuelto;
		
		// Comenzando en (0,0)
		pos[0] = 0;	pos[1] = 0;
		posResultado[0] = 0; posResultado[1] = 3;
		devuelto = FuncionesUtiles.sumarPosiciones(pos, Offset.Arriba);
		assertEquals(posResultado[0], devuelto[0]);
		assertEquals(posResultado[1], devuelto[1]);
		
		posResultado[0] = 0; posResultado[1] = 1;
		devuelto = FuncionesUtiles.sumarPosiciones(pos, Offset.Abajo);
		assertEquals(posResultado[0], devuelto[0]);
		assertEquals(posResultado[1], devuelto[1]);
		
		posResultado[0] = 1; posResultado[1] = 0;
		devuelto = FuncionesUtiles.sumarPosiciones(pos, Offset.Derecha);
		assertEquals(posResultado[0], devuelto[0]);
		assertEquals(posResultado[1], devuelto[1]);
		
		posResultado[0] = 3; posResultado[1] = 0;
		devuelto = FuncionesUtiles.sumarPosiciones(pos, Offset.Izquierda);
		assertEquals(posResultado[0], devuelto[0]);
		assertEquals(posResultado[1], devuelto[1]);
		
		// Comenzando en (3,3)
		pos[0] = 3;	pos[1] = 3;
		posResultado[0] = 3; posResultado[1] = 2;
		devuelto = FuncionesUtiles.sumarPosiciones(pos, Offset.Arriba);
		assertEquals(posResultado[0], devuelto[0]);
		assertEquals(posResultado[1], devuelto[1]);
		
		posResultado[0] = 3; posResultado[1] = 0;
		devuelto = FuncionesUtiles.sumarPosiciones(pos, Offset.Abajo);
		assertEquals(posResultado[0], devuelto[0]);
		assertEquals(posResultado[1], devuelto[1]);
		
		posResultado[0] = 0; posResultado[1] = 3;
		devuelto = FuncionesUtiles.sumarPosiciones(pos, Offset.Derecha);
		assertEquals(posResultado[0], devuelto[0]);
		assertEquals(posResultado[1], devuelto[1]);
		
		posResultado[0] = 2; posResultado[1] = 3;
		devuelto = FuncionesUtiles.sumarPosiciones(pos, Offset.Izquierda);
		assertEquals(posResultado[0], devuelto[0]);
		assertEquals(posResultado[1], devuelto[1]);*/
	}

}

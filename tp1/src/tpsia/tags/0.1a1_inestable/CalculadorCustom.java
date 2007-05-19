package tpsia.tp1;

import java.util.Vector;

import calculador.Calculador;
import calculador.Pair;

/* jeje muy groso estuviste con esto 
 * se me ocurrió lo mismo por eso te digo groso jajaja
 */
public class CalculadorCustom extends Calculador {
	private final int[] POSICION_INICIAL = {0,2};
	private final int[] POSICION_COMIDAS = {
			1,1,
			2,2,
			3,3
	};
	
	private final int[] POSICION_ENEMIGOS = {
			0,0,
			0,1,
			0,2,
			0,3
	};
	
	/** Energía inicial del pacman. */
	private final int ENERGIA_INICIAL = 20;
	
	/** Energía que gana el pacman por alimento. */
	private final int ENERGIA_COMIDA = 5;
	/** Energía que pierde el pacman por pelear. */
	private final int ENERGIA_ENEMIGO = 10;
	/** Energía que pierde el pacman por moverse. */
	private final int ENERGIA_MOVIMIENTO = 1;
	
	private int energiaPacman;
	
	private Vector posicionEnemigos;
	private Vector posicionComida;
	private Pair posicionInicial;
	
	public CalculadorCustom(String str) {
		this();
	}
	
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
	public Vector inicializarEnemigo() {
		return this.posicionEnemigos;
	}
}

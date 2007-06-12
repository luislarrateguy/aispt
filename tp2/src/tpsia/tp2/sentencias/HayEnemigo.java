package tpsia.tp2.sentencias;

import java.util.Hashtable;
import java.util.Vector;

public class HayEnemigo extends Sentencia {

	private String columna;
	private String fila;
	
	/**
	 * Constructor utilizado para realizar consultas sobre la
	 * posición actual del agente.
	 * @param tiempo
	 */ 
	public HayEnemigo(int tiempo) {
		this.columna = "X";
		this.fila = "Y";
		this.tiempo = tiempo;
	}
	
	/**
	 * Constructor utilizado para crear sentencias que se van a
	 * agregar a la base de conocimiento.
	 * @param columna
	 * @param fila
	 * @param tiempo
	 */
	public HayEnemigo (int columna, int fila, int tiempo) {
		this(tiempo);
		
		this.columna = Integer.toString(columna);
		this.fila = Integer.toString(fila);
	}
	
	public HayEnemigo (int[] posicion, int tiempo) {
		this(posicion[0], posicion[1], tiempo);
	}
	
	@Override
	public Boolean getResultado() throws SentenciaException {
		Vector<Hashtable> resultados = this.realizarConsulta();
		
		// Esto falta terminar
		return true;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}

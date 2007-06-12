package tpsia.tp2.logica.sentencias;

import java.util.Hashtable;
import java.util.Vector;

public class Posicion extends Sentencia {
	
	private String columna;
	private String fila;
	
	/**
	 * Constructor utilizado para realizar consultas sobre la
	 * posición actual del agente.
	 * @param tiempo
	 */ 
	public Posicion(int tiempo) {
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
	public Posicion (int columna, int fila, int tiempo) {
		this(tiempo);
		
		this.columna = Integer.toString(columna);
		this.fila = Integer.toString(fila);
	}
	
	public Posicion (int[] posicion, int tiempo) {
		this(posicion[0], posicion[1], tiempo);
	}
	
	@Override
	public String toString() {
		return "posicion(" +
				this.columna + "," +
				this.fila + "," +
				this.tiempo + ")";
	}
	
	/**
	 * Este método esta hecho para retornar un sólo par (x,y) y no varios,
	 * ya que ésta es la forma más común (la otra es para ingresar datos a la
	 * BC) de ser utilizada la sentencia 'posicion'.
	 * @throws SentenciaException 
	 */
	public Integer[] getResultado() throws SentenciaException {
		/* TODO: Podríamos unir el uso de excepciones con log4j, haciendo que
		 * en ellas se imprima el mensaje de error, aunque es medio al pedo quizá.
		 * Lo hago así, porque no quiero perder tiempo con log4j, vos lo manejas
		 * más.
		 */
		if (!this.columna.equals("X") && !this.fila.equals("Y"))
			throw new SentenciaException("Posicion: no se puede obtener un resultado " +
					"de una sentencia formada para ser ingresada a la BC.");
		
		// Realizo la consulta con el intérprete Prolog
		Vector<Hashtable> resultados = this.realizarConsulta();
		
		if (resultados.size() > 1)
			throw new SentenciaException("Posicion.getResultado: no se esperaba " +
					"más que un resultado.");
		
		Hashtable resultado = resultados.elementAt(0);
		
		Integer[] pos = new Integer[2];
		pos[0] = (Integer)resultado.get(this.columna);
		pos[1] = (Integer)resultado.get(this.fila);
		
		return pos;
	}
}

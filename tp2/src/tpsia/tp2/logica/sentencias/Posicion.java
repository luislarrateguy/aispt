package tpsia.tp2.logica.sentencias;

public class Posicion extends Sentencia {
	
	private Integer columna;
	private Integer fila;
	
	/**
	 * Constructor utilizado generalmente para hacer consultas a la base
	 * de conocimiento, aunque no es obligación utilizar éste.
	 */
	public Posicion() {
		super();
		
		this.columna = null;
		this.fila = null;
	}
	
	/**
	 * Constructor utilizado para crear sentencias que se van a
	 * agregar a la base de conocimiento.
	 * @param columna
	 * @param fila
	 * @param tiempo
	 */
	public Posicion (int columna, int fila, int tiempo) {
		super(tiempo);
		
		this.columna = columna;
		this.fila = fila;
	}

	public int getColumna() {
		return columna;
	}

	public void setColumna(int columna) {
		this.columna = columna;
	}

	public int getFila() {
		return fila;
	}

	public void setFila(int fila) {
		this.fila = fila;
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Posicion))
			return false;
		
		Posicion p = (Posicion)o;
		
		if (!super.equals(p) ||
				!this.columna.equals(p.columna) ||
				!this.fila.equals(p.fila))
			return false;
		
		return true;
	}
}

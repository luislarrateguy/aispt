package tpsia.tp2.logica.sentencias;

import java.util.Hashtable;
import java.util.Vector;

import tpsia.tp2.logica.prolog.Prolog;

public abstract class Sentencia {
	protected int tiempo;
	protected static Prolog interpreteProlog = new Prolog();
	
	public abstract String toString();
	public abstract Object getResultado() throws SentenciaException;
	
	protected Vector<Hashtable> realizarConsulta() {
		Vector<Hashtable> resultado = interpreteProlog.solve(this.toString());
		
		return resultado;
	}
}

package tpsia.tp2.agente;

import java.util.Hashtable;
import java.util.Vector;

import tpsia.tp2.acciones.Accion;
import tpsia.tp2.logica.Prolog;
import tpsia.tp2.logica.StmtCreator;

public class BaseConocimiento {

	private Prolog prolog;

	public BaseConocimiento() {
		this.prolog = new Prolog();
	}

	public void decir(String statement) {
		/* TODO: Se debería llamar a un método 'crearSentencia'
		 * que reciba la acción y la transforme en... sentencias :D
		 * Nacho: Hecho, con otra clase para crear todas las sentencias.
		 * Asi dejamos lugar aca (en la clase) para disparar toda la 
		 * "inferencia" de los axiomas de estado sucesor y demás.
		 */
		this.prolog.addStatement(statement);
	}

	public Accion preguntar(String statement) {
		/**
		 * Se me ocurre que este preguntar podría hacer:
		 * String s = "mejorAccion(X,"+Integer.toString(tiempo)+")";
		 * r = p.solve(s);
		 * donde el primer resultado de r, será la mejor accion posible
		 * a ejecutar. Es decir, la excelente, la muy buena, la buena, o 
		 * la que sea.
		 * mejorAccion(X,s):-excelente(X,s).
		 * mejorAccion(X,s):-muy_buena(X,s).
		 * mejorAccion(X,s):-buena(X,s).
		 * mejorAccion(X,s):-mala(X,s).
		 * 
		 * */
		return null;
	}

	public boolean cumplioObjetivo() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean agenteVivo() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Crearía un objeto VisionAmbiente a partir de esta base
	 * de conocimiento.
	 * @return
	 */
	public VisionAmbiente getVisionAmbiente() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getEnergiaAgente() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * La diferencia de <b>actuar</b> con <b>decir</b>, sería
	 * que en actuar agrego a la KDB un nuevo <i>statement</i>
	 * y además disparo los procesos de axiomas de estado sucesor
	 * y reglas causales que no se activen solas en el proceso
	 * de inferencia, tal como el caso de los promedios (sólo
	 * por eficiencia)
	 */
	public void actuar(Accion a, int tiempo) {
		/**
		 * Le dice a la KDB qué acción ejecutó.
		 */
		this.decir(StmtCreator.accionEjecutada(a,tiempo));
		/**
		 * Agrega los promedios como statements fijos
		 * si vemos que la deducción va muy lenta
		 */
		this.agregarPromedios(tiempo);
		/**
		 * Preguntará cual es el estado sucesor
		 * y agregará cada resultado como un statement
		 * a la KDB
		 */
		this.calcularEstadoSucesor(a,tiempo);
		
	}

	@SuppressWarnings("unchecked")
	private void calcularEstadoSucesor(Accion a, int tiempo) {
		/**
		 * Para cada resultado, agregarlo a la KDB
		 * (ejemplo)
		 */
		Vector<Hashtable> res = (Vector<Hashtable>) this.prolog.solve(StmtCreator.solveAxiomaUno(tiempo));
		for (Hashtable r : res) {
			String x = (String) r.get("X");
			String y = (String) r.get("Y");
			this.prolog.addStatement(StmtCreator.celdaVacia(x,y,tiempo+1));
		}
		/**
		 * Este bucle se debería repetir para cada statement que deba
		 * agregarse debido a un estado sucesor.
		 */
	}

	private void agregarPromedios(int tiempo) {
		// TODO Auto-generated method stub
		
	}

}

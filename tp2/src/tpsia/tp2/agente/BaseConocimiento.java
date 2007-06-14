package tpsia.tp2.agente;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import jpl.*;
import tpsia.tp2.Percepcion;
import tpsia.tp2.acciones.Accion;
import tpsia.tp2.logica.sentencias.Sentencia;

public class BaseConocimiento {

	private static String TEMP_PROLOG_FILE = "temp.pl";
	
	private Query prologQuery;
	private Hashtable resultados;
	private FileWriter tempPrologFile;
	
	public BaseConocimiento() throws Exception {
		super();
		
		this.tempPrologFile = new FileWriter(TEMP_PROLOG_FILE);
		this.tempPrologFile.flush();
		
		this.prologQuery = new Query("consult('base_conocimiento.pl')");
		if (!this.prologQuery.hasSolution())
			throw new Exception("Agente: Falló la carga de la base de conocimientos.");
		
		this.prologQuery.close();
		
		this.prologQuery = new Query("consult('" + TEMP_PROLOG_FILE + "')");
		if (!this.prologQuery.hasSolution())
			throw new Exception("Agente: Falló la carga del archivo temporal.");
		
		this.prologQuery.close();
		
		this.resultados = new Hashtable();
		
		/* Estaría muy bueno utilizar reflection acá. De forma que cuando agregas
		 * una sentencia nueva, no tenes que tocar absolutamente nada.
		 * Otra solución menos complicada sería utilizar constructores estáticos
		 * para las sentencias, pero esto no existe en Java, si en C# ;)
		 */
//		this.sentencias = new Hashtable<Class,HashSet<Sentencia>>();
//		
//		this.sentencias.put(CeldaVacia.class, new HashSet<Sentencia>());
//		this.sentencias.put(Conoce.class, new HashSet<Sentencia>());
//		this.sentencias.put(Energia.class, new HashSet<Sentencia>());
//		this.sentencias.put(HayComida.class, new HashSet<Sentencia>());
//		this.sentencias.put(HayEnemigo.class, new HashSet<Sentencia>());
//		this.sentencias.put(Posicion.class, new HashSet<Sentencia>());
//		this.sentencias.put(PromedioPorPelear.class, new HashSet<Sentencia>());
//		this.sentencias.put(PromedioPorAvanzar.class, new HashSet<Sentencia>());
	}

	public void decir(Percepcion p) {
		/* TODO: Se debería llamar a un método 'crearSentencia'
		 * que reciba la acción y la transforme en... sentencias :D
		 * Nacho: Hecho, con otra clase para crear todas las sentencias.
		 * Asi dejamos lugar aca (en la clase) para disparar toda la 
		 * "inferencia" de los axiomas de estado sucesor y demás.
		 */
		
		try {
			this.tempPrologFile.write(p.toString() + "\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void decir(Accion a) {
		
	}

	public Accion preguntarMejorAccion() {
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
		//this.decir(CreadorSentencias.accionEjecutada(a,tiempo));
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
//		Vector<Hashtable> res = (Vector<Hashtable>) this.prolog.solve(CreadorSentencias.solveAxiomaUno(tiempo));
//		for (Hashtable r : res) {
//			String x = (String) r.get("X");
//			String y = (String) r.get("Y");
//			this.prolog.addStatement(CreadorSentencias.celdaVacia(x,y,tiempo+1));
//		}
		/**
		 * Este bucle se debería repetir para cada statement que deba
		 * agregarse debido a un estado sucesor.
		 */
	}

	private void agregarPromedios(int tiempo) {
		// TODO Auto-generated method stub
		
	}

}

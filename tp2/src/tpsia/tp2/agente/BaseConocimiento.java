package tpsia.tp2.agente;

import jpl.JPL;
import jpl.Query;
import tpsia.tp2.Percepcion;
import tpsia.tp2.acciones.Accion;

public class BaseConocimiento {

	private static String TEMP_PROLOG_FILE = "temp.pl";
	
	private Query prologQuery;
	private int tiempo;
	
	// Para propositos de debugging
	VisionAmbiente visionAmbiente;
	
	public BaseConocimiento() throws Exception {
		super();
		
		this.tiempo = 0;
		this.visionAmbiente = new VisionAmbiente();
		
		/* Aumento la memoria disponible para el stack local, el global y el de
		 * restro. */
		JPL.setDefaultInitArgs(new String[] {
				"pl",
				"-G64m",
				"-L64m",
				"-T64m"
		});
		
		// Cargo la base de conocimiento
		this.prologQuery = new Query("consult('base_conocimiento.pl')");
		if (!this.prologQuery.hasSolution())
			throw new Exception("Agente: Falló la carga de la base de conocimientos.");
	}

	public void decir(Percepcion p) {
		this.tiempo++;
		
		// Le agrego a la percepción el parámetros situacional
		p.setTiempo(this.tiempo);
		
		this.prologQuery = new Query("assert(" + p.toString() + ")");
		this.prologQuery.hasSolution();
		
		this.visionAmbiente.actualizar(p);
	}
	
	public void decir(Accion a) {
		if (a == null)
			return;
		
		this.prologQuery = new Query("assert(accionEjecutada(" + a.getTipoAccion()
				+ "," + this.tiempo + "))");
		this.prologQuery.hasSolution();
		
		try {
			a.ejecutar(this.visionAmbiente);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Accion preguntarMejorAccion() {
		Accion a = null;
		
		this.prologQuery = new Query("mejorAccion(X," + this.tiempo + ")");
		
		String solucion = null;
		if (this.prologQuery.hasSolution())
			solucion = this.prologQuery.oneSolution().get("X").toString();
		else
			return null;
		
		for (Accion unaAccion : Accion.getAcciones()) {
			if (unaAccion.getTipoAccion().equals(solucion)) {
				a = unaAccion;
				break;
			}
		}
		
		return a;
	}

	public boolean cumplioObjetivo() {
		String s = "cumplioObjetivo(" + this.tiempo + ")";
		this.prologQuery = new Query(s);
		
		return this.prologQuery.hasSolution();
	}

	@Deprecated
	public boolean agenteVivo() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Crearía un objeto VisionAmbiente a partir de esta base
	 * de conocimiento.
	 * @return
	 */
	@Deprecated
	public VisionAmbiente getVisionAmbiente() {
		// TODO Auto-generated method stub
		return null;
	}

	@Deprecated
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
	@Deprecated
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
	@Deprecated
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

	@Deprecated
	private void agregarPromedios(int tiempo) {
		// TODO Auto-generated method stub
		
	}

	public String drawVisionAmbiente() {
		// TODO Auto-generated method stub
		return this.visionAmbiente.draw();
	}

}

package tpsia.tp1.busqueda;

import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.log4j.Logger;

import tpsia.tp1.EstadoCelda;
import tpsia.tp1.Percepcion;
import tpsia.tp1.acciones.Accion;
import tpsia.tp1.acciones.Pelear;
import tpsia.tp1.agente.Estado;
import tpsia.tp1.agente.VisionAmbiente;

public class Nodo implements Comparable<Nodo> {
	private Nodo padre;
	private ArrayList<Nodo> hijos;
	private Accion accionGeneradora;
	private static int LAST_ID = 0;
	
	private Estado estadoNodo;
	private float prioridadExpansion;
	private int id;
	
	/**
	 * Este constructor sólo debe ser utilizado para generar el
	 * nodo del estado inicial.
	 * @param estado
	 * @param prioridadExpansion
	 */
	public Nodo () {
		super();
		this.padre = null;
		this.id = Nodo.GenID();
		this.hijos = new ArrayList<Nodo>();
		this.accionGeneradora = null;
		this.prioridadExpansion = 0;
	}
	
	public Nodo (Estado estado) {
		this();
		this.estadoNodo = estado;
	}
	
	private static int GenID() {
		LAST_ID++;
		return LAST_ID;
	}

	public Nodo(Busqueda algoritmo, Nodo padre2, Accion accionGeneradora, float promVarEnergia) {
		this();
		this.padre = padre2;
		if (padre2!= null)
			padre2.addHijo(this);

		this.accionGeneradora = accionGeneradora;
		this.estadoNodo = (Estado) this.padre.estadoNodo.clone();
		
		try {
			this.accionGeneradora.ejecutar(this.estadoNodo.getAmbiente());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/* Actualizo el estado del nodo "enviándole una percepción", descubriendo como
		 * vacías las celdas desconocidas. */
		/* TODO: Se podría sacar a otro método o aplicar el patrón Factory para 
		 * crear el objeto Percepcion, ya que su creacion aca complica la lectura
		 * del resto del código de búsqueda */ 
		VisionAmbiente visionAmbienteNodoActual = this.estadoNodo.getAmbiente();
		EstadoCelda celdasAdyacentes[] = visionAmbienteNodoActual.getCeldasAdyacentes();
		EstadoCelda nuevasCeldasAdyacentes[] = new EstadoCelda[4];
		
		for (int i=0; i<4; i++) {
			nuevasCeldasAdyacentes[i] = celdasAdyacentes[i];
			
			if (celdasAdyacentes[i] == EstadoCelda.Desconocida)
				nuevasCeldasAdyacentes[i] = EstadoCelda.Vacia;
		}
		
		Percepcion p = new Percepcion(nuevasCeldasAdyacentes,
				(int)(this.estadoNodo.getEnergia() + promVarEnergia), null);
		
		this.estadoNodo.actualizarEstado(p);
		this.prioridadExpansion = algoritmo.calcularPrioridad(this);
	}
	
	private void addHijo(Nodo nodo) {
		this.hijos.add(nodo);		
	}

	/**
	 * @deprecated
	 * @param c
	 * @param accion
	 * @param var
	 * @return
	 */
	private float calcularCosto(float c, Accion accion, float var) {
		float aux;
		//TODO: No se esta usando nunca!
		/* Si no sabe lo que es pelear, que se arriesgue.
		 * Para eso le asigno un costo un poco menor que la comida. 
		 * var es la variancia */
		if (accion == Pelear.getInstancia() && 
				var == 0.0) {
			aux = (float) (c + 0.5);
		} else {
			aux = c + accion.getCosto() * ((var < 0)? 2 : 1) ;
		}
		return aux;
	}

	public int compareTo(Nodo arg0) {
		if (this.prioridadExpansion == arg0.prioridadExpansion)
			return 0;
		else if (this.prioridadExpansion < arg0.prioridadExpansion)
			return -1;
		else
			return 1;
	}

	public Estado getEstado() {
		return estadoNodo;
	}

	public Nodo getPadre() {
		return padre;
	}

	public Accion getAccionGeneradora() {
		return accionGeneradora;
	}

	public String toString() {
		return new String("Accion:"+this.accionGeneradora + " Prioridad: "+Float.toString(this.prioridadExpansion));
	}

	public float getPrioridadExpansion() {
		return prioridadExpansion;
	}

	public String getID() {
		return Integer.toString(this.id);
	}
	public static void resetID() {
		LAST_ID = 0;
	}

	@Override
	public boolean equals(Object obj) {
		Nodo n = (Nodo) obj;
		if (this.prioridadExpansion != n.prioridadExpansion) {
			return false;
		}
		
		if (!this.getEstado().getAmbiente().equals(
				n.getEstado().getAmbiente())) {
			return false;
		}
		
		return true;
	}

	public void toXML() {
		Busqueda.logxml.debug("<nodo id=\""+this.getID()+"\">");
		this.estadoNodo.toXML();
		Busqueda.logxml.debug("<prioridad>"+Float.toString(this.prioridadExpansion)+"</prioridad>");
		Busqueda.logxml.debug("<accion>"+this.accionGeneradora+"</accion>");
		Busqueda.logxml.debug("<hijos>");
		for (Nodo h : this.hijos ) {
			h.toXML();
		}
		Busqueda.logxml.debug("</hijos>");
		Busqueda.logxml.debug("</nodo>");
	}
	
	public String toQtree() {
		
		StringBuffer resultado = new StringBuffer();
		
		resultado.append("[." + this.toLatex() + " ");
		
		for (Nodo hijo : this.hijos) {
			resultado.append(hijo.toLatex() + " ");
		}
		
		resultado.append("]");
		
		return resultado.toString();
	}
	
	private String toLatex() {
		String resultado;
		
		resultado = "\\nodo"
			+ "{" + this.getID() + "}"
			+ "{" + this.getPrioridadExpansion() + "}";
		
		if (this.accionGeneradora != null)
			resultado += "{" + this.getAccionGeneradora().getTipoAccion() + "}";
		else
			resultado += "{-}";
		
		return resultado;
	}

	public static String getLastId() {
		return Integer.toString(LAST_ID);
	}
	
}
